package woowacourse.shopping.presentation.cart

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.respository.cart.CartRepository
import woowacourse.shopping.presentation.CartFixture
import woowacourse.shopping.presentation.model.CartModel
import woowacourse.shopping.presentation.view.cart.CartContract
import woowacourse.shopping.presentation.view.cart.CartPresenter

class CartPresenterTest {
    private lateinit var presenter: CartContract.Presenter
    private lateinit var view: CartContract.View
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
    }

    @Test
    fun `장바구니 데이터를 받아와 보여준다`() {
        // given
        every { cartRepository.getCarts(0, 4) } returns CartFixture.getFixture()
        justRun { view.setEnableLeftButton(false) }
        justRun { view.setEnableRightButton(false) }

        val cartItemSlot = slot<List<CartModel>>()
        val pageSlot = slot<String>()
        justRun {
            view.setCartItemsView(
                capture(cartItemSlot), "1"
            )
        }
        presenter = CartPresenter(view, cartRepository)
        // when
        presenter.loadCartItems("1")

        // then
        val actual = cartItemSlot.captured
        val expected = CartFixture.getFixture()
        assertEquals(expected, actual)
        verify { cartRepository.getCarts(0, 4) }
        verify { view.setEnableLeftButton(false) }
        verify { view.setEnableRightButton(false) }
        verify { view.setCartItemsView(actual, "1") }
    }

    @Test
    fun `카트 데이터가 하나 삭제된다`() {
        // given
        justRun { cartRepository.deleteCartByProductId(1) }
        every { cartRepository.getCarts(0, 4) } returns CartFixture.getFixture().dropLast(1)
        justRun { view.setEnableLeftButton(false) }
        justRun { view.setEnableRightButton(false) }

        presenter = CartPresenter(view, cartRepository)

        // when
        val cartItemSlot = slot<List<CartModel>>()
        justRun { view.setCartItemsView(capture(cartItemSlot), "1") }

        presenter.deleteCartItem("1", 1)

        // then
        val actual = cartItemSlot.captured
        val expected = CartFixture.getFixture().dropLast(1)
        assertEquals(expected, actual)

        verify { cartRepository.deleteCartByProductId(1) }
        verify { view.setEnableLeftButton(false) }
        verify { view.setEnableRightButton(false) }
        verify { view.setCartItemsView(actual, "1") }
    }
}
