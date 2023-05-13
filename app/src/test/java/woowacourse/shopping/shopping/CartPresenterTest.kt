package woowacourse.shopping.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.cart.CartContract
import woowacourse.shopping.cart.CartPresenter
import woowacourse.shopping.common.data.dao.CartDao
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.common.model.mapper.CartProductMapper.toView
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.URL

class CartPresenterTest {
    private lateinit var presenter: CartPresenter
    private lateinit var view: CartContract.View
    private lateinit var cart: Cart
    private lateinit var cartState: State<Cart>
    private lateinit var cartDao: CartDao

    @Before
    fun setUp() {
        view = mockk()
        cart = makeCartMock(0, 1, 2, 3, 4, 5, 6)
        cartState = mockk()
        cartDao = mockk()

        every {
            cartDao.selectAll()
        } returns cart

        every {
            cartDao.selectPage(0, 5)
        } returns makeCartMock(0, 1, 2, 3, 4)

        every {
            cartDao.selectAllCount()
        } returns 7

        every {
            cartState.save(cart)
            view.updateCart(any())
            view.updateNavigator(any(), any())
        } just runs

        presenter = CartPresenter(
            view, cart, cartState, cartDao, 0, 5
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_장바구니를_갱신한다() {
        // given

        // when

        // then
        verify {
            cartState.save(cart)
            cartDao.selectPage(0, 5)
            view.updateCart(any())
            view.updateNavigator(1, 2)
        }
    }

    @Test
    fun 장바구니_아이템을_제거하면_저장하고_뷰에_갱신한다() {
        // given
        every {
            cartState.load()
        } returns cart

        every {
            cartDao.deleteCartProductByOrdinal(any())
        } just runs

        // when
        val cartProductModel = makeCartProduct(0).toView()
        presenter.removeCartProduct(cartProductModel)

        // then
        verify {
            cartState.load()
            cartDao.deleteCartProductByOrdinal(0)
        }

        verify {
            cartState.save(cart)
            cartDao.selectPage(0, 5)
            view.updateCart(any())
            view.updateNavigator(1, 2)
        }
    }

    private fun makeCartMock(vararg cartOrdinals: Int): Cart = Cart(
        cartOrdinals.map {
            CartProduct(
                it,
                Product(
                    URL(""), "", 0
                )
            )
        }
    )

    private fun makeCartProduct(ordinal: Int): CartProduct = CartProduct(
        ordinal,
        Product(
            URL(""), "", 0
        )
    )
}
