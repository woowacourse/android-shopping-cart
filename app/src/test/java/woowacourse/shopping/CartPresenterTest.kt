package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.presentation.cart.CartContract
import woowacourse.shopping.presentation.cart.CartPresenter
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.ProductModel

class CartPresenterTest {
    private lateinit var view: CartContract.View
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository

    private fun initCartPresenter() {
        every { cartRepository.getCartProductIds(any(), any()) } returns listOf()
        every { view.initCartItems(any()) } just runs
        every { view.setCartItems(any()) } just runs
        every { view.setPage(any()) } just runs
        every { view.setLeftPageState(any()) } just runs
        every { view.setRightPageState(any()) } just runs
    }

    @Before
    fun setUp() {
        view = mockk()
        cartRepository = mockk()
        productRepository = mockk()
        initCartPresenter()
        presenter = CartPresenter(view, cartRepository, productRepository)
    }

    @Test
    fun 처음_카트_상품_목록을_설정한다() {
        // given
        val product = Product(1, "", "", Price(1000))
        val productModels = List(5) { product.toPresentation() }
        every { cartRepository.getCartProductIds(5, 0) } returns listOf(1, 1, 1, 1, 1)
        every { productRepository.findProductById(1) } returns product
        every { view.initCartItems(productModels) } just runs
        // when
        presenter.initCart()
        // then
        verify { view.initCartItems(productModels) }
    }

    @Test
    fun 상품을_삭제한다() {
        // given
        val productModel = ProductModel(1, "", "", 100)
        every { cartRepository.deleteCartProductId(1) } just runs
        // when
        presenter.deleteProduct(productModel)
        // then
        verify { cartRepository.deleteCartProductId(1) }
    }

    @Test
    fun 페이지를_1_증가시킨다() {
        // given
        every { view.setPage(2) } just runs
        // when
        presenter.plusPage()
        // then
        verify { view.setPage(2) }
    }

    @Test
    fun 페이지를_1_감소시킨다() {
        // given
        every { view.setPage(2) } just runs
        every { view.setPage(1) } just runs
        // when
        presenter.plusPage()
        presenter.minusPage()
        // then
        verify { view.setPage(1) }
    }

    @Test
    fun 카트_목록을_업데이트한다() {
        // given
        val product = Product(1, "", "", Price(1000))
        val productModels = List(5) { product.toPresentation() }
        every { cartRepository.getCartProductIds(5, 0) } returns listOf(1, 1, 1, 1, 1)
        every { productRepository.findProductById(1) } returns product
        every { view.setCartItems(productModels) } just runs
        // when
        presenter.updateCart()
        // then
        verify { view.setCartItems(productModels) }
    }

    @Test
    fun 다음_페이지에_상품이_있으면_오른쪽페이지_버튼상태를_true로_한다() {
        // given
        val product = Product(1, "", "", Price(1000))
        every { cartRepository.getCartProductIds(5, 5) } returns listOf(1, 1, 1, 1, 1)
        every { productRepository.findProductById(1) } returns product
        every { view.setRightPageState(true) } just runs
        // when
        presenter.updateRightPageState()
        // then
        verify { view.setRightPageState(true) }
    }

    @Test
    fun 다음_페이지에_상품이_없으면_오른쪽페이지_버튼상태를_false로_한다() {
        // given
        every { cartRepository.getCartProductIds(5, 5) } returns listOf()
        every { view.setRightPageState(false) } just runs
        // when
        presenter.updateRightPageState()
        // then
        verify { view.setRightPageState(false) }
    }

    @Test
    fun 현재_페이지가_1이라면_왼쪽버튼상태를_false로_한다() {
        // given
        every { view.setLeftPageState(false) } just runs
        // when
        presenter.updateLeftPageState()
        // then
        verify { view.setLeftPageState(false) }
    }

    @Test
    fun 현재_페이지가_1이_아니라면_왼쪽버튼상태를_true로_한다() {
        // given
        every { view.setLeftPageState(true) } just runs
        every { view.setPage(2) } just runs
        // when
        presenter.plusPage()
        presenter.updateLeftPageState()
        // then
        verify { view.setLeftPageState(true) }
    }
}
