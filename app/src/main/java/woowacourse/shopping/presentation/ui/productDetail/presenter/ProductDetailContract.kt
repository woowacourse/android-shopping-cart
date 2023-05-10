package woowacourse.shopping.presentation.ui.productDetail.presenter

import woowacourse.shopping.domain.model.Product

interface ProductDetailContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        val product: Product
        fun getProduct(id: Long)
        fun putRecentlyViewed(id: Long)
    }
}
