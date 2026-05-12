package woowacourse.shopping.ui.shopping

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
import woowacourse.shopping.data.network.NetworkObserver
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.data.repository.ProductRepository
import woowacourse.shopping.data.repository.RecentItemRepository
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ShoppingViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @Test
    fun `네트워크가 없을 시 상품 목록을 로드하지 않는다`() =
        runTest {
            val productRepository = FakeProductRepository(products = createProducts(size = 20))
            val viewModel =
                createViewModel(
                    productRepository = productRepository,
                    networkObserver = FakeNetworkObserver(isAvailable = false),
                )

            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.products).isEmpty()
            assertThat(productRepository.getProductsCallCount).isEqualTo(0)
        }

    @Test
    fun `네트워크가 연결되면 상품 목록을 자동으로 로드한다`() =
        runTest {
            val viewModel =
                createViewModel(
                    productRepository = FakeProductRepository(products = createProducts(size = 20)),
                    networkObserver = FakeNetworkObserver(isAvailable = true),
                )

            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.products).hasSize(20)
        }

    @Test
    fun `상품 목록 추가 로드 시 기존 목록에 합산한다`() =
        runTest {
            val viewModel =
                createViewModel(
                    productRepository = FakeProductRepository(products = createProducts(size = 25)),
                    networkObserver = FakeNetworkObserver(isAvailable = true),
                )
            mainDispatcherExtension.advanceUntilIdle()

            viewModel.loadMore()
            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.products).hasSize(25)
            assertThat(viewModel.uiState.value.canLoadMore).isFalse()
        }

    @Test
    fun `장바구니가 변경되면 장바구니 수량 상태를 갱신한다`() =
        runTest {
            val cartItemDao = TestCartItemDao()
            val viewModel =
                createViewModel(
                    cartItemDao = cartItemDao,
                    networkObserver = FakeNetworkObserver(isAvailable = false),
                )
            mainDispatcherExtension.advanceUntilIdle()

            cartItemDao.insert(createCartItemEntity(product = createProduct(id = "1"), quantity = 3))
            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.cartSize).isEqualTo(3)
            assertThat(viewModel.uiState.value.cartQuantities["1"]).isEqualTo(3)
        }

    private fun createViewModel(
        productRepository: ProductRepository =
            FakeProductRepository(products = createProducts(size = 20)),
        cartItemDao: TestCartItemDao = TestCartItemDao(),
        recentItemDao: TestRecentItemDao = TestRecentItemDao(),
        networkObserver: NetworkObserver = FakeNetworkObserver(isAvailable = true),
    ): ShoppingViewModel =
        ShoppingViewModel(
            productRepository = productRepository,
            cartRepository = CartRepository(cartItemDao),
            recentItemRepository = RecentItemRepository(recentItemDao, productRepository),
            networkObserver = networkObserver,
        )
}

private class FakeNetworkObserver(
    isAvailable: Boolean,
) : NetworkObserver {
    private val state = MutableStateFlow(isAvailable)

    override fun observeNetwork(): Flow<Boolean> = state
}

private class FakeProductRepository(
    private val products: List<Product>,
) : ProductRepository {
    var getProductsCallCount = 0
        private set

    override suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ImmutableList<Product> {
        getProductsCallCount++
        return products.drop(offset).take(limit).toImmutableList()
    }

    override suspend fun getProductById(id: String): Product =
        products.firstOrNull { it.id == id } ?: throw IllegalArgumentException("상품 없음")
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

private fun createProducts(size: Int): List<Product> =
    (1..size).map { id ->
        createProduct(id = id.toString())
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
