package woowacourse.shopping.ui.detail

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

internal class ProductDetailPresenterTest {
    private lateinit var presenter: ProductDetailContract.Presenter
    private lateinit var view: ProductDetailContract.View
    private lateinit var detailProduct: UiProduct
    private lateinit var recentProduct: UiRecentProduct

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        detailProduct = mockk(relaxed = true)
        recentProduct = mockk(relaxed = true)
        presenter = ProductDetailPresenter(view, detailProduct, recentProduct)
    }

    @Test
    internal fun 프레젠터가_초기화될_때_상품_정보를_보여준다() {
        // given
        /* ... */

        // when
        /* init */

        // then
        verify(exactly = 1) { view.showProductDetail(detailProduct) }
        verify(exactly = 1) { view.showLastViewedProductDetail(recentProduct.product) }
    }

    @Test
    internal fun 장바구니에_상품을_추가한다() {
        // given
        /* ... */

        // when
        presenter.inquiryProductCounter()

        // then
        verify(exactly = 1) { view.showProductCounter(detailProduct) }
    }
}
