package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.presentation.cart.CartContract
import woowacourse.shopping.presentation.cart.CartPresenter
import woowacourse.shopping.presentation.mapper.toPresentation
import woowacourse.shopping.presentation.model.CartProductInfoModel
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.repository.CartRepository
import woowacourse.shopping.repository.ProductRepository

class CartPresenterTest {
    private lateinit var view: CartContract.View
    private lateinit var presenter: CartContract.Presenter
    private lateinit var cartRepository: CartRepository
    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        productRepository = mockk(relaxed = true)
        presenter = CartPresenter(view, cartRepository)
    }

    @Test
    fun 상품을_삭제한다() {
        // given
        val cartProductInfoModel = CartProductInfoModel(ProductModel(1, "", "", 1000), 1)
        // when
        presenter.deleteProduct(cartProductInfoModel)
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
        val cartProductInfoList = makeCartProductInfoList(product)
        every { cartRepository.getCartProductsInfo(5, 0) } returns cartProductInfoList
        val cartProductInfoModels = cartProductInfoList.items.map { it.toPresentation() }
        // when
        presenter.updateCart()
        // then
        verify { view.setCartItems(cartProductInfoModels) }
    }

    private fun makeCartProductInfoList(product: Product): CartProductInfoList {
        return CartProductInfoList(
            List(5) { CartProductInfo(product, 1) },
        )
    }

    @Test
    fun 다음_페이지에_상품이_있으면_오른쪽페이지_버튼상태를_true로_한다() {
        // given
        val product = Product(1, "", "", Price(1000))
        every { cartRepository.getCartProductsInfo(5, 5) } returns makeCartProductInfoList(product)
        // when
        presenter.updatePlusButtonState()
        // then
        verify { view.setUpPlusPageButtonState(true) }
    }

    @Test
    fun 다음_페이지에_상품이_없으면_오른쪽페이지_버튼상태를_false로_한다() {
        // given
        every { cartRepository.getCartProductsInfo(5, 5) } returns CartProductInfoList(listOf())
        // when
        presenter.updatePlusButtonState()
        // then
        verify { view.setUpPlusPageButtonState(false) }
    }

    @Test
    fun 현재_페이지가_1이라면_왼쪽버튼상태를_false로_한다() {
        // when
        presenter.updateMinusButtonState()
        // then
        verify { view.setUpMinusPageButtonState(false) }
    }

    @Test
    fun 현재_페이지가_1이_아니라면_왼쪽버튼상태를_true로_한다() {
        // when
        presenter.plusPage()
        presenter.updateMinusButtonState()
        // then
        verify { view.setUpMinusPageButtonState(true) }
    }
}
