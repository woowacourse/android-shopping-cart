package woowacourse.shopping.shopping

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.common.data.database.dao.CartDao
import woowacourse.shopping.common.data.database.state.State
import woowacourse.shopping.domain.Cart
import woowacourse.shopping.productdetail.ProductDetailContract
import woowacourse.shopping.productdetail.ProductDetailPresenter

class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailPresenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var cartDao: CartDao

    @Before
    fun setUP() {
        view = mockk(relaxed = true)
        cartDao = mockk(relaxed = true)
    }

    @Test
    fun 프레젠터가_생성되면_뷰의_상품_상세정보를_갱신한다() {
        // given
        justRun { view.updateProductDetail(any()) }

        // when
        presenter = ProductDetailPresenter(view, mockk(relaxed = true), mockk(), mockk(), mockk())

        // then
        verify {
            view.updateProductDetail(any())
        }
    }

    @Test
    fun 카트에_상품을_담으면_카트에_상품을_추가하고_카트를_보여준다() {
        // given
        justRun { view.showCart() }
        val cartState: State<Cart> = mockk(relaxed = true)
        presenter = ProductDetailPresenter(view, mockk(relaxed = true), mockk(), mockk(), mockk())

        // when
        presenter.addToCart()

        // then
        verify { view.showCart() }
    }
}
