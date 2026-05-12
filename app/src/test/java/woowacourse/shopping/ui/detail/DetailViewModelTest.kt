package woowacourse.shopping.ui.detail

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import woowacourse.shopping.MainDispatcherExtension
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.dao.RecentItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.data.localdb.entity.RecentItemEntity
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentItemRepository
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @Test
    fun `상품을 불러오면 상품 정보와 수량과 총 가격을 반영한다`() =
        runTest {
            val viewModel = createViewModel(id = "1")

            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.product.id).isEqualTo("1")
            assertThat(viewModel.uiState.value.quantity).isEqualTo(1)
            assertThat(viewModel.uiState.value.totalPrice).isEqualTo(2000)
        }

    @Test
    fun `장바구니에 담긴 수량이 있으면 상세 수량 초기값으로 설정한다`() =
        runTest {
            val cartItemDao = TestCartItemDao()
            cartItemDao.insert(createCartItemEntity(product = createProduct(id = "1"), quantity = 3))

            val viewModel = createViewModel(id = "1", cartItemDao = cartItemDao)
            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.quantity).isEqualTo(3)
            assertThat(viewModel.uiState.value.totalPrice).isEqualTo(6000)
        }

    @Test
    fun `마지막으로 본 상품이 존재할 시 마지막으로 본 상품을 제공한다`() =
        runTest {
            val recentItemDao = TestRecentItemDao()
            recentItemDao.insert(createRecentItemEntity(product = createProduct(id = "2"), timestamp = 100L))

            val viewModel = createViewModel(id = "1", recentItemDao = recentItemDao)
            mainDispatcherExtension.advanceUntilIdle()

            val recentItem = viewModel.uiState.value.recentItem
            assertThat(recentItem?.id).isEqualTo("2")
        }

    @Test
    fun `마지막으로 본 상품 숨김 옵션이 true이면 최근 본 상품을 제공하지 않는다`() =
        runTest {
            val recentItemDao = TestRecentItemDao()
            recentItemDao.insert(createRecentItemEntity(product = createProduct(id = "2"), timestamp = 100L))

            val viewModel =
                createViewModel(
                    id = "1",
                    hideRecentItem = true,
                    recentItemDao = recentItemDao,
                )
            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.recentItem).isNull()
        }

    @Test
    fun `수량 증가 시 총 가격도 증가한다`() =
        runTest {
            val viewModel = createViewModel(id = "1")
            mainDispatcherExtension.advanceUntilIdle()

            viewModel.updateQuantity(2)

            assertThat(viewModel.uiState.value.quantity).isEqualTo(2)
            assertThat(viewModel.uiState.value.totalPrice).isEqualTo(4000)
        }

    @Test
    fun `상세 화면 진입 시 해당 상품을 최근 본 상품으로 저장한다`() =
        runTest {
            val recentItemDao = TestRecentItemDao()

            createViewModel(id = "1", recentItemDao = recentItemDao)
            mainDispatcherExtension.advanceUntilIdle()

            assertThat(recentItemDao.getRecentItemById("1")).isNotNull()
        }

    private fun createViewModel(
        id: String,
        hideRecentItem: Boolean = false,
        productRepository: ProductRepository = FakeProductRepository(products = listOf(createProduct(id = "1"), createProduct(id = "2"))),
        cartItemDao: TestCartItemDao = TestCartItemDao(),
        recentItemDao: TestRecentItemDao = TestRecentItemDao(),
    ): DetailViewModel =
        DetailViewModel(
            id = id,
            hideRecentItem = hideRecentItem,
            productRepository = productRepository,
            cartRepository = CartRepository(cartItemDao),
            recentItemRepository = RecentItemRepository(recentItemDao, productRepository),
        )
}

private class FakeProductRepository(
    private val products: List<Product>,
    private val failOnGetProductById: Boolean = false,
    private val getProductByIdException: Exception? = null,
) : ProductRepository {
    override suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ImmutableList<Product> = products.drop(offset).take(limit).toImmutableList()

    override suspend fun getProductById(id: String): Product {
        getProductByIdException?.let { throw it }
        if (failOnGetProductById) throw IllegalArgumentException("상품 조회 실패")
        return products.firstOrNull { it.id == id } ?: throw IllegalArgumentException("상품 없음")
    }
}

private class TestCartItemDao : CartItemDao {
    private val items = MutableStateFlow<List<CartItemEntity>>(emptyList())

    override fun getAll(): Flow<List<CartItemEntity>> = items

    override suspend fun insert(item: CartItemEntity) {
        items.value = items.value.filterNot { it.id == item.id } + item
    }

    override suspend fun findById(id: String): CartItemEntity? = items.value.firstOrNull { it.id == id }

    override suspend fun deleteById(id: String) {
        items.value = items.value.filterNot { it.id == id }
    }

    override suspend fun getTotalCount(): Int = items.value.size

}

private class TestRecentItemDao : RecentItemDao {
    private val items = MutableStateFlow<List<RecentItemEntity>>(emptyList())

    override suspend fun insert(item: RecentItemEntity) {
        items.value = items.value.filterNot { it.id == item.id } + item
    }

    override fun getRecentItems(): Flow<List<RecentItemEntity>> =
        items.map { entities ->
            entities.sortedWith(compareByDescending<RecentItemEntity> { it.timestamp }.thenByDescending { it.id }).take(10)
        }

    override suspend fun getRecentItemById(id: String): RecentItemEntity? = items.value.firstOrNull { it.id == id }

    override suspend fun deleteOldItem() {
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

private fun createProduct(id: String): Product =
    Product(
        id = id,
        name = ProductName("상품$id"),
        price = Money(2000),
        imageUrl = "image$id",
    )

private fun createCartItemEntity(
    product: Product,
    quantity: Int,
): CartItemEntity =
    CartItemEntity(
        id = product.id,
        quantity = quantity,
        timestamp = 100L,
    )

private fun createRecentItemEntity(
    product: Product,
    timestamp: Long,
): RecentItemEntity =
    RecentItemEntity(
        id = product.id,
        timestamp = timestamp,
    )
