package woowacourse.shopping.presentation.cart

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.getOrAwaitValue
import woowacourse.shopping.presentation.cart.viewmodel.CartViewModel
import woowacourse.shopping.presentation.dummy.DummyCartItems
import woowacourse.shopping.presentation.dummy.DummyProducts

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var cartViewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        cartViewModel = CartViewModel(FakeCartRepository(DummyCartItems().carts), FakeProductRepository(DummyProducts().products))
    }

    @Test
    fun `장바구니에 상품이 5개보다 많아도 5개씩 불러온다`() {
        val orders = cartViewModel.orders

        cartViewModel.loadCurrentPageCartItems()

        assertThat(orders.getOrAwaitValue()).hasSize(5)
    }

    @Test
    fun `다음 상품들을 불러오면 장바구니 2개를 가지고 있다`() {
        val orders = cartViewModel.orders

        cartViewModel.loadNextPageCartItems()

        assertThat(orders.getOrAwaitValue()).hasSize(2)
    }

    @Test
    fun `이전 페이지로 이동하면 장바구니 5개를 가지고 있다`() {
        val orders = cartViewModel.orders

        cartViewModel.loadNextPageCartItems()
        cartViewModel.loadPreviousPageCartItems()

        assertThat(orders.getOrAwaitValue()).hasSize(5)
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제할 수 있다`() {
        val orders = cartViewModel.orders

        cartViewModel.loadCurrentPageCartItems()
        cartViewModel.removeAllCartItem(1)
        cartViewModel.removeAllCartItem(2)
        cartViewModel.removeAllCartItem(3)
        cartViewModel.removeAllCartItem(4)
        cartViewModel.removeAllCartItem(5)
        cartViewModel.removeAllCartItem(6)

        assertThat(orders.getOrAwaitValue()).hasSize(1)
    }
}
