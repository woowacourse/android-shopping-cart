package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

interface ProductDetailContract {
    interface View {
        val presenter: Presenter

        fun showProductDetail(product: UiProduct)
        fun showLastViewedProductDetail(product: UiProduct?)
        fun showProductCounter(product: UiProduct)
        fun navigateToProductDetail(recentProduct: UiRecentProduct)
        fun navigateToHome(product: UiProduct, count: Int)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun inquiryProductCounter()
        abstract fun inquiryLastViewedProduct()
        abstract fun addBasketProductCount(count: Int)
    }
}
