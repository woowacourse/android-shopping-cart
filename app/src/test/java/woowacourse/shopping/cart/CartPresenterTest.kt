package woowacourse.shopping.cart

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.createCartProduct
import woowacourse.shopping.createCartProductModel
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.database.dao.CartDao
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.repository.CartRepository

class CartPresenterTest {
    private lateinit var presenter: CartPresenter
    private val view: CartContract.View = mockk()
    private val cartRepository: CartRepository = mockk()

    @Before
    fun setUP() {
        every { cartRepository.getPage(any(), any()) } returns Cart(emptyList())
        every { cartRepository.getAllCount() } returns 0
        every { cartRepository.getTotalPrice() } returns 0
        every { cartRepository.getTotalAmount() } returns 0
        every { view.updateNavigationVisibility(any()) } just runs
        every { view.updateCart(any(), any(), any()) } just runs
        every { view.updateCartTotalPrice(any()) } just runs
        every { view.updateCartTotalAmount(any()) } just runs
        every { cartRepository.isAllCheckedInPage(any(), any()) } returns true
        every { view.updateAllChecked(any()) } just runs

        presenter = CartPresenter(
            view, cartRepository, 0, 0
        )
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_장바구니를_갱신한다() {
        // given

        // when

        // then
        verify {
            view.updateNavigationVisibility(any())
            view.updateCart(any(), any(), any())
        }
    }

    @Test
    fun 프레젠터가_생성되면_총_가격을_업데이트_한다() {
        // given

        // when

        // then
        verify { view.updateCartTotalPrice(any()) }
    }

    @Test
    fun 프레젠터가_생성되면_총_수량을_업데이트_한다() {
        // given

        // when

        // then
        verify { view.updateCartTotalAmount(any()) }
    }

    @Test
    fun 장바구니_아이템을_제거하면_저장하고_뷰에_갱신한다() {
        // given
        every { cartRepository.deleteCartProduct(any()) } just runs
        every { view.setResultForChange() } just runs

        // when
        val cartProductModel = createCartProductModel()
        presenter.removeCartProduct(cartProductModel)

        // then
        verify {
            cartRepository.deleteCartProduct(any())
            view.setResultForChange()
        }
    }

    @Test
    fun 페이지를_넘기면_레포지토리에서_카트를_가져온다() {
        // given

        // when
        presenter.goToNextPage()

        // then
        verify {
            cartRepository.getPage(any(), any())
        }
    }

    @Test
    fun 카트의_사이즈보다_현재_페이지와_페이지당_사이즈의_곱이_작다면_캐싱되어_있는_카트를_가져온다() {
        // given
        val cartDao: CartDao = mockk()
        val cart: Cart = mockk()
        val cartRepository = CartRepositoryImpl(cartDao, cart)
        every { cartDao.selectAllCount() } returns 0
        every { cartDao.getTotalPrice() } returns 0
        every { cartDao.getTotalAmount() } returns 0
        every { cart.cartProducts } returns listOf(createCartProduct())
        every { cart.cartProducts.size } returns 1
        every { cart.getSubCart(any(), any()) } returns Cart(listOf(createCartProduct()))

        // when
        presenter = CartPresenter(
            view, cartRepository, 0, 1
        )

        // then
        verify { cart.getSubCart(any(), any()) }
    }

    @Test
    fun 카트_상품의_체크_상태를_업데이트_하면_카트_상품이_교체되고_뷰에_업데이트_된다() {
        // given
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        presenter.changeCartProductChecked(createCartProductModel())

        // then
        verify {
            cartRepository.replaceCartProduct(any(), any())
            view.updateCartProduct(any(), any())
        }
    }

    @Test
    fun 카트_상품의_체크_상태를_업데이트_하면_총_가격이_업데이트_된다() {
        // given
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs
        every { view.updateCartTotalPrice(any()) } just runs

        // when
        presenter.changeCartProductChecked(createCartProductModel())

        // then
        verify(exactly = 2) { view.updateCartTotalPrice(any()) }
    }

    @Test
    fun 카트_상품의_체크_상태를_업데이트_하면_총_수량이_업데이트_된다() {
        // given
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs
        every { view.updateCartTotalAmount(any()) } just runs

        // when
        presenter.changeCartProductChecked(createCartProductModel())

        // then
        verify(exactly = 2) { view.updateCartTotalAmount(any()) }
    }

    @Test
    fun 전체_체크_상태를_업데이트_하면_페이지내의_모든_상품이_체크되어_있는지_확인하고_뷰의_전체_체크를_업데이트_한다() {
        // given

        // when
        presenter.updateAllChecked()

        // then
        verify(exactly = 2) {
            cartRepository.isAllCheckedInPage(any(), any())
            view.updateAllChecked(any())
        }
    }
}
