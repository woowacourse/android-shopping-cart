package woowacourse.shopping.presentation.view.productdetail

import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun showProductInfoView(productModel: ProductModel)
        fun handleErrorView()
        fun addCartSuccessView()
    }
    interface Presenter {
        fun loadProductInfoById(id: Long)
        fun addCart(productId: Long)
    }
}
