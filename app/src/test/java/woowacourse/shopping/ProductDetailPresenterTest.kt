package woowacourse.shopping

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.data.cart.CartRepository
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.ProductDetailContract
import woowacourse.shopping.presentation.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk()
        cartRepository = mockk()
        presenter = ProductDetailPresenter(view, cartRepository)
    }

    @Test
    fun 카트에_상품_목록을_추가한다() {
        // given
        val productModel = ProductModel(10, "", "", 1000)
        every { cartRepository.addCartProductId(productModel.id) } just runs
        every { view.showCompleteMessage(productModel.name) } just runs
        // when
        presenter.putProductInCart(productModel)
        // then
        verify { view.showCompleteMessage(productModel.name) }
    }
}
