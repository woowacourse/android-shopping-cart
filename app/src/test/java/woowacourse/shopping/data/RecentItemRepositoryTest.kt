package woowacourse.shopping.data

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.data.localdb.dao.RecentItemDao
import woowacourse.shopping.data.localdb.entity.RecentItemEntity
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentItemRepository
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

class RecentItemRepositoryTest {
    private val product =
        Product(
            id = "1",
            name = ProductName("상품"),
            price = Money(2000),
            imageUrl = "image-url",
        )

    @Test
    fun `최근 본 상품 저장 후 오래된 상품을 삭제한다`() =
        runTest {
            val dao = TestRecentItemDao()
            val repository = RecentItemRepository(dao, FakeProductRepository(listOf(product)))

            repository.addRecentItem(product)

            val savedItem = dao.getRecentItemById(product.id)
            assertThat(savedItem?.id).isEqualTo(product.id)
            assertThat(dao.deleteOldItemCount).isEqualTo(1)
        }

    @Test
    fun `최근 본 상품 목록을 도메인 상품으로 변환해 반환한다`() =
        runTest {
            val dao = TestRecentItemDao()
            val repository = RecentItemRepository(dao, FakeProductRepository(listOf(product)))
            dao.insert(product.toRecentItemEntity(timestamp = 100L))

            val recentItems = repository.getRecentItems().first()

            assertThat(recentItems).hasSize(1)
            assertThat(recentItems[0].id).isEqualTo(product.id)
            assertThat(recentItems[0].getName()).isEqualTo(product.getName())
            assertThat(recentItems[0].getPrice()).isEqualTo(product.getPrice())
            assertThat(recentItems[0].imageUrl).isEqualTo(product.imageUrl)
        }

    @Test
    fun `마지막으로 본 상품을 반환한다`() =
        runTest {
            val dao = TestRecentItemDao()
            val repository = RecentItemRepository(dao, FakeProductRepository(listOf(createProduct(id = "1"), createProduct(id = "2"))))
            dao.insert(createProduct(id = "1").toRecentItemEntity(timestamp = 100L))
            dao.insert(createProduct(id = "2").toRecentItemEntity(timestamp = 200L))

            val lastViewedItem = repository.getLastViewedItem()

            assertThat(lastViewedItem?.id).isEqualTo("2")
        }

    @Test
    fun `최근 본 상품이 없으면 마지막으로 본 상품은 null이 반환된다`() =
        runTest {
            val repository = RecentItemRepository(TestRecentItemDao(), FakeProductRepository(emptyList()))

            val lastViewedItem = repository.getLastViewedItem()

            assertThat(lastViewedItem).isNull()
        }

    private fun createProduct(id: String): Product =
        Product(
            id = id,
            name = ProductName("product$id"),
            price = Money(1000),
            imageUrl = "image$id",
        )

    private fun Product.toRecentItemEntity(timestamp: Long): RecentItemEntity =
        RecentItemEntity(
            id = id,
            timestamp = timestamp,
        )

    private class TestRecentItemDao : RecentItemDao {
        private val items = MutableStateFlow<List<RecentItemEntity>>(emptyList())
        var deleteOldItemCount = 0
            private set

        override suspend fun insert(item: RecentItemEntity) {
            items.value = items.value.filterNot { it.id == item.id } + item
        }

        override fun getRecentItems(): Flow<List<RecentItemEntity>> =
            items.map { entities ->
                entities.sortedWith(compareByDescending<RecentItemEntity> { it.timestamp }.thenByDescending { it.id }).take(10)
            }

        override suspend fun getRecentItemById(id: String): RecentItemEntity? = items.value.firstOrNull { it.id == id }

        override suspend fun deleteOldItem() {
            deleteOldItemCount++
            val recentIds =
                items.value
                    .sortedWith(compareByDescending<RecentItemEntity> { it.timestamp }.thenByDescending { it.id })
                    .take(10)
                    .map { it.id }
                    .toSet()
            items.value = items.value.filter { it.id in recentIds }
        }

        override suspend fun getLastViewedItem(): RecentItemEntity? =
            items.value.maxWithOrNull(compareBy<RecentItemEntity> { it.timestamp }.thenBy { it.id })
    }

    private class FakeProductRepository(
        private val products: List<Product>,
    ) : ProductRepository {
        override suspend fun getProducts(
            offset: Int,
            limit: Int,
        ): ImmutableList<Product> = products.drop(offset).take(limit).toImmutableList()

        override suspend fun getProductById(id: String): Product =
            products.firstOrNull { it.id == id } ?: throw IllegalArgumentException()
    }
}
