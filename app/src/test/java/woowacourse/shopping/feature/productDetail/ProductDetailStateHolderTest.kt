package woowacourse.shopping.feature.productDetail

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.Price
import woowacourse.shopping.domain.model.Quantity
import woowacourse.shopping.domain.model.cart.CartItem
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.model.product.ProductItems
import woowacourse.shopping.domain.model.product.ProductTitle
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.ProductRepository
import woowacourse.shopping.domain.repository.RecentProductRepository

class ProductDetailStateHolderTest {
    private val product =
        Product(
            id = "product-1",
            imageUrl = "url",
            productTitle = ProductTitle("상품"),
            price = Price(1000),
        )

    @Test
    fun `상세 화면은 장바구니 수량이 아닌 선택 수량 1로 시작한다`() =
        runTest {
            val cartRepository = FakeCartRepository(initialItems = listOf(CartItem(product, Quantity(5))))

            val stateHolder = createStateHolder(cartRepository)
            advanceUntilIdle()

            assertThat(stateHolder.productInfo?.formattedQuantity).isEqualTo("1")
        }

    @Test
    fun `플러스와 마이너스 버튼은 SSOT를 변경하지 않고 선택 수량만 변경한다`() =
        runTest {
            val cartRepository = FakeCartRepository(initialItems = listOf(CartItem(product, Quantity(5))))

            val stateHolder = createStateHolder(cartRepository)
            advanceUntilIdle()
            stateHolder.onIncreaseClick()
            stateHolder.onIncreaseClick()
            stateHolder.onDecreaseClick()
            advanceUntilIdle()

            assertThat(stateHolder.productInfo?.formattedQuantity).isEqualTo("2")
            assertThat(cartRepository.currentQuantity(product.id)).isEqualTo(5)
        }

    @Test
    fun `장바구니 담기 버튼을 누르면 선택 수량이 기존 SSOT 수량에 더해진다`() =
        runTest {
            val cartRepository = FakeCartRepository(initialItems = listOf(CartItem(product, Quantity(5))))

            val stateHolder = createStateHolder(cartRepository)
            advanceUntilIdle()
            stateHolder.onIncreaseClick()
            stateHolder.onIncreaseClick()
            stateHolder.onAddClick()
            advanceUntilIdle()

            assertThat(cartRepository.currentQuantity(product.id)).isEqualTo(8)
        }

    @Test
    fun `마이너스 버튼은 선택 수량을 1 미만으로 낮추지 않는다`() =
        runTest {
            val cartRepository = FakeCartRepository()

            val stateHolder = createStateHolder(cartRepository)
            advanceUntilIdle()
            stateHolder.onDecreaseClick()
            stateHolder.onAddClick()
            advanceUntilIdle()

            assertThat(stateHolder.productInfo?.formattedQuantity).isEqualTo("1")
            assertThat(cartRepository.currentQuantity(product.id)).isEqualTo(1)
        }

    private fun TestScope.createStateHolder(cartRepository: CartRepository): ProductDetailStateHolder {
        val dispatcher = StandardTestDispatcher(testScheduler)
        return ProductDetailStateHolder(
            productRepository = FakeProductRepository(product),
            cartRepository = cartRepository,
            recentProductRepository = FakeRecentProductRepository(),
            productId = product.id,
            isFromRecent = false,
            scope = TestScope(dispatcher),
            ioDispatcher = dispatcher,
        )
    }

    private class FakeProductRepository(
        private val product: Product,
    ) : ProductRepository {
        override suspend fun getProducts(
            page: Int,
            pageSize: Int,
        ): ProductItems = ProductItems(listOf(product))

        override suspend fun getProductCount(): Int = 1

        override suspend fun getProduct(id: String): Product? = product.takeIf { it.id == id }
    }

    private class FakeCartRepository(
        initialItems: List<CartItem> = emptyList(),
    ) : CartRepository {
        private val cartItems = MutableStateFlow(initialItems)

        override fun getCartItems(): Flow<List<CartItem>> = cartItems

        override fun getCartItem(productId: String): Flow<CartItem?> =
            cartItems.map { items -> items.firstOrNull { it.product.id == productId } }

        override suspend fun updateCart(cartItem: CartItem) {
            cartItems.value = cartItems.value.filterNot { it.product.id == cartItem.product.id } + cartItem
        }

        override suspend fun deleteCartItem(productId: String) {
            cartItems.value = cartItems.value.filterNot { it.product.id == productId }
        }

        override suspend fun increaseCartItemQuantity(productId: String) {
            val item = cartItems.value.firstOrNull { it.product.id == productId } ?: return
            updateCart(item.copy(quantity = Quantity(item.quantity.value + 1)))
        }

        override suspend fun decreaseCartItemQuantity(productId: String) {
            val item = cartItems.value.firstOrNull { it.product.id == productId } ?: return
            if (item.quantity.value > 1) {
                updateCart(item.copy(quantity = Quantity(item.quantity.value - 1)))
            }
        }

        override suspend fun getCartItemCount(): Int = cartItems.value.size

        override suspend fun getPagingCartItems(
            page: Int,
            pageSize: Int,
        ): List<CartItem> = cartItems.value.drop(page * pageSize).take(pageSize)

        fun currentQuantity(productId: String): Int? =
            cartItems.value.firstOrNull { it.product.id == productId }?.quantity?.value
    }

    private class FakeRecentProductRepository : RecentProductRepository {
        private val recentProducts = MutableStateFlow<List<Product>>(emptyList())

        override fun getRecentProducts(): Flow<List<Product>> = recentProducts

        override suspend fun saveRecentProduct(product: Product) {
            recentProducts.value = recentProducts.value.filterNot { it.id == product.id } + product
        }
    }
}
