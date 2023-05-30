package woowacourse.shopping.presentation.view.productdetail

import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun showProductInfoView(product: ProductModel)
        fun showRecentProductById(product: ProductModel)
        fun handleErrorView()
        fun addCartSuccessView()
    }
    interface Presenter {
        fun loadProductInfoById(id: Long)
        fun loadRecentProductById(id: Long)
        fun addCart(productId: Long, count: Int)
    }
}
