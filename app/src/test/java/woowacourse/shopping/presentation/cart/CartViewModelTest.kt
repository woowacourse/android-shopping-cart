package woowacourse.shopping.presentation.cart

import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.shopping.InstantTaskExecutorExtension
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.domain.repository.FakeCartRepository
import woowacourse.shopping.domain.repository.FakeProductRepository
import woowacourse.shopping.domain.repository.ProductRepository

@ExtendWith(InstantTaskExecutorExtension::class)
class CartViewModelTest {
    private lateinit var cartViewModel: CartViewModel

    @BeforeEach
    fun setUp() {
        cartViewModel = CartViewModel(FakeCartRepository(), FakeProductRepository())
    }

    @Test
    fun `장바구니에 담아놓은 상품들을 불러올 수 있다`() {
        cartViewModel.loadCurrentPageCartItems()
        val orders = cartViewModel.orders.value
        assertThat(orders).isEqualTo(
            listOf(
                Order(cartItemId = 1, image = "", productName = "Product 1", price = 1000, quantity = 1),
                Order(cartItemId = 2, image = "", productName = "Product 2", price = 2000, quantity = 1),
                Order(cartItemId = 3, image = "", productName = "Product 3", price = 3000, quantity = 1),
                Order(cartItemId = 4, image = "", productName = "Product 4", price = 4000, quantity = 1),
                Order(cartItemId = 5, image = "", productName = "Product 5", price = 5000, quantity = 1),
            ),
        )
    }

    @Test
    fun `장바구니에 담긴 상품을 삭제할 수 있다`() {
        val cartRepository = mockk<CartRepository>(relaxed = true)
        val productRepository = mockk<ProductRepository>(relaxed = true)
        val viewmodel = CartViewModel(cartRepository, productRepository)
        viewmodel.removeCartItem(1)
        verify { cartRepository.removeCartItem(1) }
        verify { cartRepository.fetchCartItems(0) }
    }
}
