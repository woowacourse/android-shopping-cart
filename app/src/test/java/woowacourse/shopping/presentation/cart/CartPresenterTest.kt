package woowacourse.shopping.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.data.respository.cart.CartTotalPriceRepository
import woowacourse.shopping.presentation.CartProductFixture
import woowacourse.shopping.presentation.model.CartProductModel
import woowacourse.shopping.presentation.view.cart.CartContract
import woowacourse.shopping.presentation.view.cart.CartPresenter

internal class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository
    private lateinit var cartTotalPriceRepository: CartTotalPriceRepository

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        cartTotalPriceRepository = mockk(relaxed = true)
    }

    @Test
    fun `장바구니 데이터를 받아와 보여준다`() {
        // given
        every { cartRepository.getCarts(0, 4) } returns CartProductFixture.getFixture()

        val cartItemSlot = slot<List<CartProductModel>>()
        justRun {
            view.setCartItemsView(
                capture(cartItemSlot)
            )
        }
        presenter = CartPresenter(view, cartRepository, cartTotalPriceRepository)
        // when
        presenter.loadCartItems()

        // then
        val actual = cartItemSlot.captured
        val expected = CartProductFixture.getFixture()
        assertEquals(expected, actual)
        verify { cartRepository.getCarts(0, 4) }
        verify { view.setCartItemsView(actual) }
    }

    @Test
    fun `카트 데이터가 하나 삭제된다`() {
        // given
        justRun { cartRepository.deleteCartByProductId(1) }
        every { cartRepository.getCarts(0, 4) } returns CartProductFixture.getFixture().dropLast(1)

        presenter = CartPresenter(view, cartRepository, cartTotalPriceRepository)

        // when
        val cartItemSlot = slot<List<CartProductModel>>()
        justRun { view.setCartItemsView(capture(cartItemSlot)) }

        presenter.deleteCartItem(1)

        // then
        val actual = cartItemSlot.captured
        val expected = CartProductFixture.getFixture().dropLast(1)
        assertEquals(expected, actual)

        verify { cartRepository.deleteCartByProductId(1) }
        verify { view.setCartItemsView(actual) }
    }

    @Test
    fun `카트에 있는 총 합 가격을 계산한다`() {
        every { cartTotalPriceRepository.getTotalPrice() } returns 10000
        presenter = CartPresenter(view, cartRepository, cartTotalPriceRepository)
        // when
        presenter.loadCartItems()
        // then
        assertEquals(presenter.totalPrice.value, 10000)
        verify { cartRepository.getCarts(0, 4) }
    }
}
