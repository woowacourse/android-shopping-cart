package woowacourse.shopping.cart

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.createCartProduct
import woowacourse.shopping.createCartProductModel
import woowacourse.shopping.createProduct
import woowacourse.shopping.createProductModel
import woowacourse.shopping.data.cart.CartRepositoryImpl
import woowacourse.shopping.data.database.dao.CartDao
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.domain.repository.CartRepository
import java.time.LocalDateTime

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

    @Test
    fun 카트_상품_수량을_증가시키면_레포지토리의_카트_상품을_수정_및_교체하고_뷰의_카트_상품을_업데이트_한다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 0, false, createProductModel())
        presenter.increaseCartProductAmount(cartProductModel)

        // then
        verify {
            cartRepository.modifyCartProduct(any())
            cartRepository.replaceCartProduct(any(), any())
            view.updateCartProduct(any(), any())
        }
    }

    @Test
    fun 카트_상품이_체크_상태가_아닐_때_카트_상품_수량을_증가시키면_총_가격과_수량이_업데이트_되지_않아_총_한_번_업데이트_한다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 0, false, createProductModel())
        presenter.increaseCartProductAmount(cartProductModel)

        // then
        verify(exactly = 1) {
            view.updateCartTotalPrice(any())
            view.updateCartTotalAmount(any())
        }
    }

    @Test
    fun 카트_상품이_체크_상태일_때_카트_상품_수량을_증가시키면_총_가격과_수량이_업데이트_되어_총_두_번_업데이트_된다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 0, true, createProductModel())
        presenter.increaseCartProductAmount(cartProductModel)

        // then
        verify(exactly = 2) {
            view.updateCartTotalPrice(any())
            view.updateCartTotalAmount(any())
        }
    }

    @Test
    fun 카트_상품_수량이_1_보다_작거나_같으면_수량을_감소시켜도_레포지토리의_카트_상품을_수정_및_교체하지_않고_뷰의_카트_상품을_업데이트_하지_않는다() {
        // given

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 1, false, createProductModel())
        presenter.decreaseCartProductAmount(cartProductModel)

        // then
        verify(exactly = 0) {
            cartRepository.modifyCartProduct(any())
            cartRepository.replaceCartProduct(any(), any())
            view.updateCartProduct(any(), any())
        }
    }

    @Test
    fun 카트_상품_수량을_감소시키면_레포지토리의_카트_상품을_수정_및_교체하고_뷰의_카트_상품을_업데이트_한다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 2, false, createProductModel())
        presenter.decreaseCartProductAmount(cartProductModel)

        // then
        verify {
            cartRepository.modifyCartProduct(any())
            cartRepository.replaceCartProduct(any(), any())
            view.updateCartProduct(any(), any())
        }
    }

    @Test
    fun 카트_상품이_체크_상태가_아닐_때_카트_상품_수량을_감소시키면_총_가격과_수량이_업데이트_되지_않아_총_한_번_업데이트_한다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 2, false, createProductModel())
        presenter.decreaseCartProductAmount(cartProductModel)

        // then
        verify(exactly = 1) {
            view.updateCartTotalPrice(any())
            view.updateCartTotalAmount(any())
        }
    }

    @Test
    fun 카트_상품이_체크_상태일_때_카트_상품_수량을_감소시키면_총_가격과_수량이_업데이트_되어_총_두_번_업데이트_된다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
        every { cartRepository.replaceCartProduct(any(), any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 2, true, createProductModel())
        presenter.decreaseCartProductAmount(cartProductModel)

        // then
        verify(exactly = 2) {
            view.updateCartTotalPrice(any())
            view.updateCartTotalAmount(any())
        }
    }

    @Test
    fun 체크된_상품_2개와_체크되지_않은_상품_3개가_있을_때_전체_체크를_하면_체크되지_않은_3개의_상품만큼만_업데이트_된다() {
        // given
        val cart = Cart(
            listOf(
                CartProduct(LocalDateTime.now(), 0, false, createProduct()),
                CartProduct(LocalDateTime.now(), 0, true, createProduct()),
                CartProduct(LocalDateTime.now(), 0, false, createProduct()),
                CartProduct(LocalDateTime.now(), 0, true, createProduct()),
                CartProduct(LocalDateTime.now(), 0, false, createProduct()),
            )
        )
        val cartRepository = CartRepositoryImpl(mockk(relaxed = true), cart)
        presenter = CartPresenter(
            view, cartRepository, 0, 5
        )
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        presenter.updateCartProductCheckedInPage(true)

        // then
        val expected = 3
        verify(exactly = expected) { view.updateCartProduct(any(), any()) }
    }
}
