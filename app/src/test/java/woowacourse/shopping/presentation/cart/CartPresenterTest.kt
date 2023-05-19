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

        every { cartRepository.getAllCarts() } returns CartFixture.getFixture()
    }

    @Test
    fun `장바구니 데이터를 받아와 보여준다`() {
        // given
        justRun { view.setEnableLeftButton(false) }
        justRun { view.setEnableRightButton(false) }

        presenter = CartPresenter(view, cartRepository)

        val slot = slot<List<CartModel>>()
        justRun { view.setCartItemsView(capture(slot)) }

        // when
        presenter.loadCartItems()

        // then
        val actual = slot.captured
        verify { view.setEnableLeftButton(false) }
        verify { view.setEnableRightButton(false) }
        verify { view.setCartItemsView(actual) }
    }

    @Test
    fun `카트 데이터가 하나 삭제된다`() {
        // given
        justRun { cartRepository.deleteCartByCartId(1) }
        justRun { view.setEnableLeftButton(false) }
        justRun { view.setEnableRightButton(false) }

        presenter = CartPresenter(view, cartRepository)

        val slot = slot<List<CartModel>>()
        justRun { view.setCartItemsView(capture(slot)) }

        // when
        presenter.deleteCartItem(1)

        // then
        val actual = slot.captured
        val expected = CartFixture.getFixture().filter { it.id != 1L }
        assertEquals(expected.size, actual.size)
        verify { cartRepository.deleteCartByCartId(1) }
        verify { view.setEnableLeftButton(false) }
        verify { view.setEnableRightButton(false) }
        verify { view.setCartItemsView(actual) }
    }

    @Test
    fun `2페이지에서 이전 페이지인 1페이지로 이동한다`() {
        // given
        val slot = slot<Int>()
        justRun { view.setPageCountView(capture(slot)) }

        presenter = CartPresenter(view, cartRepository, currentPage = 2)

        // when
        presenter.calculatePreviousPage()

        // then
        val actual = slot.captured
        val expected = 1
        assertEquals(expected, actual)
        verify { view.setPageCountView(actual) }
    }

    @Test
    fun `1페이지에서 다음 페이진 2페이지로 이동한다`() {
        // given
        val slot = slot<Int>()
        justRun { view.setPageCountView(capture(slot)) }

        presenter = CartPresenter(view, cartRepository, currentPage = 1)

        // when
        presenter.calculateNextPage()

        // then
        val actual = slot.captured
        val expected = 2
        assertEquals(expected, actual)
        verify { view.setPageCountView(actual) }
    }
}
