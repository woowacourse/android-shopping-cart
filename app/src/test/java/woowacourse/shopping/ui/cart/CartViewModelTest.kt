package woowacourse.shopping.ui.cart

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.shopping.domain.Money
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Quantity
import woowacourse.shopping.domain.repository.CartRepository
import woowacourse.shopping.ui.cart.viewmodel.CartViewModel
import woowacourse.shopping.ui.productlist.MockCartRepository

class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private lateinit var cartRepository: CartRepository

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        cartRepository = MockCartRepository()
        viewModel = CartViewModel(cartRepository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `장바구니 아이템의 수량을 증가시키면 아이템이 증가한다`() = runTest {
        advanceUntilIdle()

        val product = Product("상품1", Money(1000), "", "1")
        cartRepository.addCartItem(product, Quantity(1))
        advanceUntilIdle()

        viewModel.incrementQuantity("1")
        advanceUntilIdle()

        val cartItems = cartRepository.getCart().first()

        assertEquals(1, cartItems.size)
        assertEquals(2, cartItems[0].quantity.count)
    }

    @Test
    fun `장바구니 아이템의 수량을 감소시키면 아이템이 감소한다`() = runTest {
        advanceUntilIdle()

        val product = Product("상품1", Money(1000), "", "1")
        cartRepository.addCartItem(product, Quantity(2))
        advanceUntilIdle()

        viewModel.decrementQuantity("1")
        advanceUntilIdle()

        val cartItems = cartRepository.getCart().first()

        assertEquals(1, cartItems.size)
        assertEquals(1, cartItems[0].quantity.count)
    }

    @Test
    fun `장바구니 아이템의 수량이 1일때 수량을 감소시키면 아이템이 삭제된다`() = runTest {
        advanceUntilIdle()

        val product = Product("상품1", Money(1000), "", "1")
        cartRepository.addCartItem(product, Quantity(1))
        advanceUntilIdle()

        viewModel.decrementQuantity("1")
        advanceUntilIdle()

        val cartItems = cartRepository.getCart().first()

        assertEquals(0, cartItems.size)
    }
}
