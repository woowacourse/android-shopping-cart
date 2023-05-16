package woowacourse.shopping

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.productdetail.ProductDetailContract
import woowacourse.shopping.presentation.productdetail.ProductDetailPresenter
import woowacourse.shopping.repository.CartRepository

class ProductDetailPresenterTest {
    private lateinit var view: ProductDetailContract.View
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var cartRepository: CartRepository

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        cartRepository = mockk(relaxed = true)
        presenter = ProductDetailPresenter(view, cartRepository, ProductModel(10, "", "", 1000))
    }

    @Test
    fun 카트에_상품_목록을_추가한다() {
        // given
        val productModel = ProductModel(10, "", "", 1000)
        // when
        presenter.putProductInCart()
        // then
        verify { view.showCompleteMessage(productModel.name) }
    }
}
