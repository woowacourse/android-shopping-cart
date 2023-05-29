package woowacourse.shopping.cart

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.mapper.CartProductMapper.toView
import woowacourse.shopping.createCartProductModel
import woowacourse.shopping.createProduct
import woowacourse.shopping.createProductModel
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
        every { view.updateAllChecked(any()) } just runs

        presenter = CartPresenter(
            view, cartRepository, Page(0), 0
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
    fun 장바구니_아이템을_제거하면_아이템을_제거한다() {
        // given
        every { cartRepository.deleteCartProduct(any()) } just runs

        // when
        val cartProductModel = createCartProductModel()
        presenter.removeCartProduct(cartProductModel)

        // then
        verify {
            cartRepository.deleteCartProduct(any())
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
    fun 카트_상품의_체크_상태를_업데이트_하면_카트_상품이_교체되고_뷰에_업데이트_된다() {
        // given
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        presenter.reverseCartProductChecked(createCartProductModel())

        // then
        verify {
            view.updateCartProduct(any(), any())
        }
    }

    @Test
    fun 카트_상품의_체크_상태를_업데이트_하면_총_가격이_업데이트_된다() {
        // given
        every { view.updateCartProduct(any(), any()) } just runs
        every { view.updateCartTotalPrice(any()) } just runs

        // when
        presenter.reverseCartProductChecked(createCartProductModel())

        // then
        verify(exactly = 2) { view.updateCartTotalPrice(any()) }
    }

    @Test
    fun 카트_상품의_체크_상태를_업데이트_하면_총_수량이_업데이트_된다() {
        // given
        every { view.updateCartProduct(any(), any()) } just runs
        every { view.updateCartTotalAmount(any()) } just runs

        // when
        presenter.reverseCartProductChecked(createCartProductModel())

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
            view.updateAllChecked(any())
        }
    }

    @Test
    fun 카트_상품_수량을_증가시키면_레포지토리의_카트_상품을_수정_및_교체하고_뷰의_카트_상품을_업데이트_한다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 0, false, createProductModel())
        presenter.increaseCartProductAmount(cartProductModel)

        // then
        verify {
            cartRepository.modifyCartProduct(any())
            view.updateCartProduct(any(), any())
        }
    }

    @Test
    fun 카트_상품이_체크_상태가_아닐_때_카트_상품_수량을_증가시키면_총_가격과_수량이_업데이트_되지_않아_총_한_번_업데이트_한다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
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
            view.updateCartProduct(any(), any())
        }
    }

    @Test
    fun 카트_상품_수량을_감소시키면_레포지토리의_카트_상품을_수정_및_교체하고_뷰의_카트_상품을_업데이트_한다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
        every { view.updateCartProduct(any(), any()) } just runs

        // when
        val cartProductModel = CartProductModel(LocalDateTime.now(), 2, false, createProductModel())
        presenter.decreaseCartProductAmount(cartProductModel)

        // then
        verify {
            cartRepository.modifyCartProduct(any())
            view.updateCartProduct(any(), any())
        }
    }

    @Test
    fun 카트_상품이_체크_상태가_아닐_때_카트_상품_수량을_감소시키면_총_가격과_수량이_업데이트_되지_않아_총_한_번_업데이트_한다() {
        // given
        every { cartRepository.modifyCartProduct(any()) } just runs
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
    fun 카트_아이템_수량이_다르면_카트에_변화가_있음을_알린다() {
        // given
        val time = LocalDateTime.now()
        val initialCart = Cart(listOf(CartProduct(time, 2, true, createProduct())))
        val cart = Cart(listOf(CartProduct(time, 1, true, createProduct())))
        presenter = CartPresenter(
            view, cartRepository, Page(0), 0, initialCart, cart
        )
        every { view.notifyProductsChanged() } just runs

        // when
        presenter.checkProductsChanged()

        // then
        verify(exactly = 1) {
            view.notifyProductsChanged()
        }
    }

    @Test
    fun 카트_아이템_수량이_같으면_카트에_변화가_있음을_알리지_않는다() {
        // given
        val time = LocalDateTime.now()
        val initialCart = Cart(listOf(CartProduct(time, 2, true, createProduct())))
        val cart = Cart(listOf(CartProduct(time, 2, true, createProduct())))
        presenter = CartPresenter(
            view, cartRepository, Page(0), 0, initialCart, cart
        )
        every { view.notifyProductsChanged() } just runs

        // when
        presenter.checkProductsChanged()

        // then
        verify(exactly = 0) {
            view.notifyProductsChanged()
        }
    }

    @Test
    fun 카트_아이템_선택_여부가_달라도_카트에_변화가_있음을_알리지_않는다() {
        // given
        val time = LocalDateTime.now()
        val initialCart = Cart(listOf(CartProduct(time, 2, true, createProduct())))
        val cart = Cart(listOf(CartProduct(time, 2, false, createProduct())))
        presenter = CartPresenter(
            view, cartRepository, Page(0), 0, initialCart, cart
        )
        every { view.notifyProductsChanged() } just runs

        // when
        presenter.checkProductsChanged()

        // then
        verify(exactly = 0) {
            view.notifyProductsChanged()
        }
    }

    @Test
    fun 카트_아이템이_삭제되면_카트에_변화가_있음을_알린다() {
        // given
        val cartProduct = CartProduct(LocalDateTime.now(), 2, true, createProduct())
        val initialCart = Cart(listOf(cartProduct))
        val cart = Cart(listOf(cartProduct))
        presenter = CartPresenter(
            view, cartRepository, Page(0), 0, initialCart, cart
        )
        every { view.notifyProductsChanged() } just runs
        every { cartRepository.deleteCartProduct(any()) } just runs

        // when
        presenter.removeCartProduct(cartProduct.toView())
        presenter.checkProductsChanged()

        // then
        verify(exactly = 1) {
            view.notifyProductsChanged()
        }
    }

    @Test
    fun 페이지가_첫_페이지라면_이전_페이지로_이동할_수_없다() {
        // given
        val page: Page = mockk()
        every { page.isFirstPage() } returns true
        every { page.value } returns 0
        presenter = CartPresenter(
            view, cartRepository, page, 0
        )

        // when
        presenter.goToPreviousPage()

        // then
        verify(exactly = 0) { page.moveToPreviousPage() }
    }

    @Test
    fun 페이지가_첫_페이지가_아니라면_이전_페이지로_이동할_수_있다() {
        // given
        val page: Page = mockk()
        every { page.isFirstPage() } returns false
        every { page.value } returns 1
        every { page.moveToPreviousPage() } returns Page(0)
        presenter = CartPresenter(
            view, cartRepository, page, 0
        )

        // when
        presenter.goToPreviousPage()

        // then
        verify(exactly = 1) { page.moveToPreviousPage() }
    }
}
