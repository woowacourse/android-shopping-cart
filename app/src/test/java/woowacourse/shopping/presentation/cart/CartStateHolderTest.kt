package woowacourse.shopping.presentation.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.model.cart.Cart
import woowacourse.shopping.domain.model.product.Product
import woowacourse.shopping.domain.repository.CartRepository
import kotlin.math.min
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class FakeCartRepository(
    private var cart: Cart,
) : CartRepository {
    override fun getItems(): Cart = cart

    override fun getPagingItems(
        page: Int,
        pageSize: Int,
    ): Cart {
        if (page < 0 || pageSize <= 0) return Cart()

        val fromIndex = page * pageSize

        if (fromIndex >= getTotalItemCount()) {
            return Cart()
        }

        val toIndex = min(fromIndex + pageSize, cart.cartItems.size)
        return Cart(cart.cartItems.subList(fromIndex, toIndex))
    }

    override fun getTotalItemCount(): Int = cart.cartItems.size

    override fun addProduct(product: Product) {
        cart = cart.addProductToCart(product)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun deleteProduct(productId: Uuid) {
        cart = cart.deleteProductFromCart(productId)
    }
}

@OptIn(ExperimentalUuidApi::class)
class CartStateHolderTest {
    @Test
    fun `초기화 시 첫 페이지 CartItem을 보여준다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        assertThat(stateHolder.cart.cartItems).isEqualTo(CartFixture.cartItems.take(2))
    }

    @Test
    fun `초기화 시 현재 페이지는 1이다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        assertThat(stateHolder.currentPage).isEqualTo(1)
    }

    @Test
    fun `상품 개수가 pageSize보다 많으면 hasMoreItems는 true이다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        assertThat(stateHolder.hasMoreItems).isTrue()
    }

    @Test
    fun `상품 개수가 pageSize 이하이면 hasMoreItems는 false이다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 10,
                onPageIndexChanged = {},
            )

        assertThat(stateHolder.hasMoreItems).isFalse()
    }

    @Test
    fun `첫 페이지에서는 hasPreviousPage가 false이다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        assertThat(stateHolder.hasPreviousPage).isFalse()
    }

    @Test
    fun `다음 페이지가 있으면 hasNextPage가 true이다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        assertThat(stateHolder.hasNextPage).isTrue()
    }

    @Test
    fun `goToNextPage 호출 시 currentPage가 증가하고 다음 페이지 CartItem을 보여준다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        stateHolder.goToNextPage()
        assertThat(stateHolder.cart.cartItems).isEqualTo(CartFixture.cartItems.subList(2, 4))
        assertThat(stateHolder.currentPage).isEqualTo(2)
        assertThat(stateHolder.hasPreviousPage).isTrue()
    }

    @Test
    fun `goToPreviousPage 호출 시 currentPage가 감소하고 이전 페이지 CartItem을 보여준다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        stateHolder.goToNextPage()
        stateHolder.goToPreviousPage()
        assertThat(stateHolder.cart.cartItems).isEqualTo(CartFixture.cartItems.take(2))
        assertThat(stateHolder.currentPage).isEqualTo(1)
    }

    @Test
    fun `상품 삭제 시 Cart에서 해당 상품이 삭제된다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        stateHolder.deleteProduct(CartFixture.cartItems[0].productId)

        assertThat(stateHolder.cart.cartItems).isEqualTo(CartFixture.cartItems.subList(1, 3))
    }

    @Test
    fun `마지막 페이지의 유일한 상품을 삭제하면 이전 페이지로 보정한다`() {
        val repository =
            FakeCartRepository(
                cart = Cart(cartItems = CartFixture.cartItems.take(3)),
            )
        val stateHolder =
            CartStateHolder(
                cartRepository = repository,
                pageSize = 2,
                onPageIndexChanged = {},
            )

        stateHolder.goToNextPage()
        stateHolder.deleteProduct(CartFixture.cartItems[2].productId)

        assertThat(stateHolder.cart.cartItems).isEqualTo(CartFixture.cartItems.take(2))
        assertThat(stateHolder.currentPage).isEqualTo(1)
    }
}
