package woowacourse.shopping.presentation.view.productdetail

import woowacourse.shopping.presentation.model.ProductModel

interface ProductDetailContract {
    interface View {
        fun setProductInfoView(productModel: ProductModel)
        fun handleErrorView()
    }
    interface Presenter {
        fun loadProductInfoById(id: Long)
    }
}
