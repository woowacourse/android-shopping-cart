package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

interface ProductDetailContract {
    interface View {
        val presenter: Presenter

        fun showProductDetail(product: UiProduct)
        fun showLastViewedProductDetail(product: UiProduct?)
        fun navigateToBasketScreen()
        fun navigateToProductDetail(recentProduct: UiRecentProduct)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun addBasketProduct()
        abstract fun inquiryLastViewedProduct()
    }
}
