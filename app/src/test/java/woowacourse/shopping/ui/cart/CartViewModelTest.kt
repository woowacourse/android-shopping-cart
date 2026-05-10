package woowacourse.shopping.ui.cart

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import woowacourse.shopping.MainDispatcherExtension
import woowacourse.shopping.data.localdb.dao.CartItemDao
import woowacourse.shopping.data.localdb.entity.CartItemEntity
import woowacourse.shopping.data.repository.CartRepository
import woowacourse.shopping.model.Money
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductName

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {
    @JvmField
    @RegisterExtension
    val mainDispatcherExtension = MainDispatcherExtension()

    @Test
    fun `장바구니 첫 페이지의 상품 5개를 반영한다`() =
        runTest {
            val cartItemDao = TestCartItemDao()
            insertCartItems(cartItemDao, size = 6)

            val viewModel = CartViewModel(CartRepository(cartItemDao))
            mainDispatcherExtension.advanceUntilIdle()

            val items = viewModel.uiState.value.items
            val productIds = items.map { it.product.id }
            assertThat(productIds).containsExactly("1", "2", "3", "4", "5")
            assertThat(viewModel.uiState.value.isCanMoveNext).isTrue()
        }

    @Test
    fun `다음 페이지로 이동하면 다음 상품 목록을 반영한다`() =
        runTest {
            val cartItemDao = TestCartItemDao()
            insertCartItems(cartItemDao, size = 6)
            val viewModel = CartViewModel(CartRepository(cartItemDao))
            mainDispatcherExtension.advanceUntilIdle()

            viewModel.nextPage()

            val items = viewModel.uiState.value.items
            val productIds = items.map { it.product.id }
            assertThat(productIds).containsExactly("6")
            assertThat(viewModel.uiState.value.page).isEqualTo(1)
            assertThat(viewModel.uiState.value.isCanMoveNext).isFalse()
        }

    @Test
    fun `이전 페이지로 이동하면 이전 상품 목록이 제공된다`() =
        runTest {
            val cartItemDao = TestCartItemDao()
            insertCartItems(cartItemDao, size = 6)
            val viewModel = CartViewModel(CartRepository(cartItemDao))
            mainDispatcherExtension.advanceUntilIdle()
            viewModel.nextPage()

            viewModel.previousPage()

            val items = viewModel.uiState.value.items
            val productIds = items.map { it.product.id }
            assertThat(productIds).containsExactly("1", "2", "3", "4", "5")
            assertThat(viewModel.uiState.value.page).isEqualTo(0)
        }

    @Test
    fun `상품 삭제 후 해당 페이지에 상품이 없을 경우 이전 페이지로 보정한다`() =
        runTest {
            val cartItemDao = TestCartItemDao()
            insertCartItems(cartItemDao, size = 6)
            val viewModel = CartViewModel(CartRepository(cartItemDao))
            mainDispatcherExtension.advanceUntilIdle()
            viewModel.nextPage()

            viewModel.deleteItem("6")
            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.page).isEqualTo(0)
            val items = viewModel.uiState.value.items
            val productIds = items.map { it.product.id }
            assertThat(productIds).containsExactly("1", "2", "3", "4", "5")
        }

    @Test
    fun `장바구니 총 상품 개수와 총 가격을 반영한다`() =
        runTest {
            val cartItemDao = TestCartItemDao()
            insertCartItems(cartItemDao, size = 2)

            val viewModel = CartViewModel(CartRepository(cartItemDao))
            mainDispatcherExtension.advanceUntilIdle()

            assertThat(viewModel.uiState.value.totalCartSize).isEqualTo(2)
            assertThat(viewModel.uiState.value.totalPrice).isEqualTo(4000)
        }

    private suspend fun insertCartItems(
        cartItemDao: TestCartItemDao,
        size: Int,
    ) {
        (1..size).forEach { id ->
            cartItemDao.insert(
                createCartItemEntity(
                    product = createProduct(id = id.toString()),
                    quantity = 1,
                    timestamp = id.toLong(),
                ),
            )
        }
    }
}

private class TestCartItemDao : CartItemDao {
    private val items = MutableStateFlow<List<CartItemEntity>>(emptyList())

    override fun getAll(): Flow<List<CartItemEntity>> = items

    override suspend fun insert(item: CartItemEntity) {
        items.value = (items.value.filterNot { it.id == item.id } + item).sortedBy { it.timestamp }
    }

    override suspend fun findById(id: String): CartItemEntity? = items.value.firstOrNull { it.id == id }

    override suspend fun deleteById(id: String) {
        items.value = items.value.filterNot { it.id == id }
    }

    override suspend fun getTotalCount(): Int = items.value.size

    override suspend fun getTotalPrice(): Int = items.value.sumOf { it.price * it.quantity }
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
    timestamp: Long = 100L,
): CartItemEntity =
    CartItemEntity(
        id = product.id,
        name = product.getName(),
        price = product.getPrice(),
        imageUrl = product.imageUrl,
        quantity = quantity,
        timestamp = timestamp,
    )
