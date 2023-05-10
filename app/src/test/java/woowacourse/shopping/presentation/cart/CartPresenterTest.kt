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
        cartRepository = mockk()
    }

    @Test
    fun `장바구니 데이터를 받아와 보여준다`() {
        // given
        every { cartRepository.getCarts() } returns CartFixture.getFixture()

        presenter = CartPresenter(view, cartRepository)

        val slot = slot<List<CartModel>>()
        justRun { view.setCartItemsView(capture(slot)) }

        // when
        presenter.loadCartItems()

        // then
        val actual = slot.captured
        val expected = CartFixture.getFixture()
        assertEquals(expected, actual)
        verify { cartRepository.getCarts() }
        verify { view.setCartItemsView(actual) }
    }

    @Test
    fun `카트 데이터가 하나 삭제된다`() {
        // given
        val position = 0
        justRun { cartRepository.deleteCartByProductId(1) }
        every { cartRepository.getCarts() } returns CartFixture.getFixture()

        val slot = slot<Int>()
        justRun { view.updateToDeleteCartItemView(capture(slot)) }

        presenter = CartPresenter(view, cartRepository)

        // when
        presenter.deleteCartItem(position)

        // then
        val actual = slot.captured
        val expected = 0
        assertEquals(expected, actual)
        verify { cartRepository.deleteCartByProductId(1) }
        verify { view.updateToDeleteCartItemView(actual) }
    }
}
