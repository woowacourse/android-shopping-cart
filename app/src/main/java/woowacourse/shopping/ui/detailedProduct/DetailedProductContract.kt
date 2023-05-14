package woowacourse.shopping.ui.detailedProduct

import woowacourse.shopping.model.ProductUIModel

interface DetailedProductContract {
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
