package woowacourse.shopping.presentation.cart

import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.data.model.CartItem
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.FakeCartRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var cartViewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        cartViewModel = CartViewModel(FakeCartRepository())
    }

    @Test
    fun `장바구니에 담아놓은 상품들을 불러올 수 있다`() {
        cartViewModel.loadCartItems(0)
        val cartItems = cartViewModel.cartItems.value
        assertThat(cartItems).isEqualTo(
            listOf(
                CartItem(1, 1, 1),
                CartItem(2, 2, 1),
                CartItem(3, 3, 1),
                CartItem(4, 4, 1),
                CartItem(5, 5, 1),
            ),
        )
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제할 수 있다`() {
        val repository = mockk<CartRepository>(relaxed = true)
        val viewmodel = CartViewModel(repository)
        viewmodel.removeCartItem(0, 1)
        verify { repository.removeCartItem(1) }
        verify { repository.fetchCartItems(0) }
    }
}
