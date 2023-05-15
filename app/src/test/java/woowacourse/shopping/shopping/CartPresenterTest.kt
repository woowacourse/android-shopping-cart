package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.cart.CartContract
import woowacourse.shopping.cart.CartPresenter
import woowacourse.shopping.data.database.dao.CartDao
import woowacourse.shopping.data.state.State
import woowacourse.shopping.domain.Cart

class CartPresenterTest {
    private lateinit var presenter: CartPresenter
    private lateinit var view: CartContract.View
    private lateinit var cartState: State<Cart>
    private lateinit var cartDao: CartDao

    @Before
    fun setUP() {
        view = mockk(relaxed = true)
        cartState = mockk(relaxed = true)
        cartDao = mockk(relaxed = true)

        every {
            cartDao.selectPage(any(), any())
        } returns Cart(emptyList())

        presenter = CartPresenter(
            view, mockk(relaxed = true), cartState, cartDao, 0, 0
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_장바구니를_갱신한다() {
        // given
        justRun {
            view.updateNavigationVisibility(any())
            view.updateCart(any(), any(), any())
        }

        // when

        // then
        verify {
            view.updateNavigationVisibility(any())
            view.updateCart(any(), any(), any())
        }
    }

    @Test
    fun 장바구니_아이템을_제거하면_저장하고_뷰에_갱신한다() {
        // given
        every {
            cartState.load()
        } returns mockk(relaxed = true)

        justRun {
            cartState.save(any())
            cartDao.deleteCartProductByTime(any())
            view.updateCart(any(), any(), any())
        }

        // when
        presenter.removeCartProduct(mockk(relaxed = true))

        // then
        verify {
            cartState.save(any())
            cartDao.deleteCartProductByTime(any())
            view.updateCart(any(), any(), any())
        }
    }
}
