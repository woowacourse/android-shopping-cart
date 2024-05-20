package woowacourse.shopping.feature.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.cart.FakeCartRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.imageUrl
import woowacourse.shopping.model.CartItem
import woowacourse.shopping.model.Product
import woowacourse.shopping.model.Quantity
import woowacourse.shopping.price
import woowacourse.shopping.title

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: CartRepository

    @Test
    fun `장바구니에 담긴 상품을 삭제한다`() {
        // given
        val cartItems = cartItems(1)
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.deleteCartItem(cartItems.first())

        // then
        assertThat(viewModel.cart.getOrAwaitValue()).hasSize(0)
    }

    @Test
    fun `한 페이지에는 5개의 장바구니 상품이 있다`() {
        // given
        val cartItems = cartItems(5)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(cartRepository)

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(cartItems.take(5))
    }

    @Test
    fun `장바구니에 3개의 상품이 있는 경우 1페이지에는 3개의 상품이 보인다`() {
        // given
        val cartItems = cartItems(3)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(cartRepository)

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(3)
        assertThat(actual).isEqualTo(cartItems.take(3))
    }

    @Test
    fun `장바구니에 5개의 상품이 있는 경우 페이지 이동 버튼이 보이지 않는다`() {
        // given
        val cartItems = cartItems(5)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(cartRepository)

        // then
        val actual = viewModel.hasPage.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `장바구니 상품이 6개인 경우 1페이지에는 5개의 상품이 보인다`() {
        // given
        val cartItems = cartItems(6)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(cartRepository)

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(cartItems.take(5))
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 1페이지인 경우 다음 페이지로 이동할 수 있다`() {
        // given
        val cartItems = cartItems(6)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(cartRepository)

        // then
        val actual = viewModel.hasNextPage.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 1페이지인 경우 이전 페이지로 이동할 수 없다`() {
        // given
        val cartItems = cartItems(6)
        cartRepository = FakeCartRepository(cartItems)

        // when
        viewModel = CartViewModel(cartRepository)

        // then
        val actual = viewModel.hasPreviousPage.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 2페이지인 경우 이전 페이지로 이동할 수 있다`() {
        // given
        val cartItems = cartItems(6)
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.moveNextPage()

        // then
        val actual = viewModel.hasPreviousPage.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 2페이지인 경우 다음 페이지로 이동할 수 없다`() {
        // given
        val cartItems = cartItems(6)
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.moveNextPage()

        // then
        val actual = viewModel.hasNextPage.getOrAwaitValue()
        assertThat(actual).isFalse
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 1페이지에서 2페이지로 이동하면 1개의 상품이 보인다`() {
        // given
        val cartItems = cartItems(6)
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(cartRepository)

        // when
        viewModel.moveNextPage()

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(1)
        assertThat(actual).isEqualTo(cartItems.slice(5..5))
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 2페이지에서 1페이지로 이동하면 5개의 상품이 보인다`() {
        // given
        val cartItems = cartItems(6)
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(cartRepository)
        viewModel.moveNextPage()

        // when
        viewModel.movePreviousPage()

        // then
        val actual = viewModel.cart.getOrAwaitValue()
        assertThat(actual).hasSize(5)
        assertThat(actual).isEqualTo(cartItems.take(5))
    }

    @Test
    fun `장바구니에 6개의 상품이 있고 2페이지에서 1개의 상품을 삭제하면 1페이지로 이동한다`() {
        // given
        val cartItems = cartItems(6)
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(cartRepository)
        viewModel.moveNextPage()

        // when
        val lastOneCartItem = viewModel.cart.getOrAwaitValue().first()
        viewModel.deleteCartItem(lastOneCartItem)

        // then
        val actual = viewModel.page.getOrAwaitValue()
        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `장바구니에 1개의 상품이 있고 1개의 상품을 삭제하면 장바구니가 비어있다`() {
        // given
        val cartItems = cartItems(1)
        cartRepository = FakeCartRepository(cartItems)
        viewModel = CartViewModel(cartRepository)

        // when
        val cartItem = viewModel.cart.getOrAwaitValue().first()
        viewModel.deleteCartItem(cartItem)

        // then
        val actual = viewModel.isEmptyCart.getOrAwaitValue()
        assertThat(actual).isTrue
    }

    private fun cartItems(size: Int): List<CartItem> {
        val product = Product(0L, imageUrl, title, price)
        return List(size) { CartItem(it.toLong(), product.copy(id = it.toLong()), Quantity(0)) }
    }
}
