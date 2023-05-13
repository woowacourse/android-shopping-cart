package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.model.ProductUIModel

interface ProductDetailContract {
    interface View {
        fun setProductDetail(product: ProductUIModel)
        fun navigateToCart()
    }

    interface Presenter {
        fun setUpProductDetail()
        fun addProductToCart()
        fun addProductToRecent()
    }
}
