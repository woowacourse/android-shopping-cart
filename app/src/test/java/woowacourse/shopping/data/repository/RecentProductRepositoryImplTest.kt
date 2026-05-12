package woowacourse.shopping.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.DUMMY_PRODUCTS
import woowacourse.shopping.data.local.RecentProductDao
import woowacourse.shopping.data.local.RecentProductEntity
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems
import woowacourse.shopping.domain.repository.ProductRepository

class RecentProductRepositoryImplTest {
    private lateinit var recentProductDao: FakeRecentProductDao
    private lateinit var repository: RecentProductRepositoryImpl
    private val products = DUMMY_PRODUCTS

    @BeforeEach
    fun setUp() {
        recentProductDao = FakeRecentProductDao()
        repository = RecentProductRepositoryImpl(recentProductDao, FakeProductRepository(products))
    }

    @Test
    fun `최근 본 상품을 저장하면 가장 최근 순서로 조회된다`() =
        runTest {
            repository.saveRecentProduct(products[0])
            repository.saveRecentProduct(products[1])

            val result = repository.getRecentProducts().first()

            assertThat(result.map { it.id }).containsExactly("product-2", "product-1")
        }

    @Test
    fun `최근 본 상품은 최대 10개까지만 유지한다`() =
        runTest {
            products.take(12).forEach { repository.saveRecentProduct(it) }

            val result = repository.getRecentProducts().first()

            assertThat(result).hasSize(10)
            assertThat(result.first().id).isEqualTo("product-12")
            assertThat(result.last().id).isEqualTo("product-3")
        }

    @Test
    fun `동일한 상품을 다시 저장하면 목록 내 순서가 최상단으로 갱신된다`() =
        runTest {
            repository.saveRecentProduct(products[0])
            repository.saveRecentProduct(products[1])
            repository.saveRecentProduct(products[0])

            val result = repository.getRecentProducts().first()

            assertThat(result.map { it.id }).containsExactly("product-1", "product-2")
        }

    @Test
    fun `존재하지 않는 상품 ID가 섞여있으면 해당 상품은 무시하고 반환한다`() =
        runTest {
            recentProductDao.insertWithLimit(products[0].toRecentEntity(timestamp = 1L))
            recentProductDao.insertWithLimit(
                RecentProductEntity(
                    id = "missing-product",
                    imageUrl = "url",
                    name = "없는 상품",
                    timestamp = 2L,
                ),
            )

            val result = repository.getRecentProducts().first()

            assertThat(result.map { it.id }).containsExactly("product-1")
        }

    private class FakeRecentProductDao : RecentProductDao {
        private var nextTimestamp = 0L
        private val recentProducts = MutableStateFlow<List<RecentProductEntity>>(emptyList())

        override fun getRecentProducts(): Flow<List<RecentProductEntity>> = recentProducts

        override suspend fun insert(recentProduct: RecentProductEntity) {
            val entity = recentProduct.copy(timestamp = ++nextTimestamp)
            recentProducts.value =
                (recentProducts.value.filterNot { it.id == entity.id } + entity)
                    .sortedByDescending { it.timestamp }
        }

        override suspend fun deleteOldProducts() {
            recentProducts.value = recentProducts.value.take(10)
        }
    }

    private class FakeProductRepository(
        products: List<Product>,
    ) : ProductRepository {
        private val productsById = products.associateBy { it.id }

        override suspend fun getProducts(
            page: Int,
            pageSize: Int,
        ): ProductItems = ProductItems(productsById.values.drop(page * pageSize).take(pageSize))

        override suspend fun getProductCount(): Int = productsById.size

        override suspend fun getProduct(id: String): Product? = productsById[id]
    }

    private fun Product.toRecentEntity(timestamp: Long): RecentProductEntity =
        RecentProductEntity(
            id = id,
            imageUrl = imageUrl,
            name = productTitle.value,
            timestamp = timestamp,
        )
}
