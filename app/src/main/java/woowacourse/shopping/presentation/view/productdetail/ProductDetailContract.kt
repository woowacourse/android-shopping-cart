package woowacourse.shopping.presentation.view.productdetail

import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun setProductInfoView(productModel: ProductModel)
        fun handleErrorView()
        fun addCartSuccessView()
        fun exitProductDetailView()
    }
    interface Presenter {
        fun loadProductInfo()
        fun addCart()
    }
}
