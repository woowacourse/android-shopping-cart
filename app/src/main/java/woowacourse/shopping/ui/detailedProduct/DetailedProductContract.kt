package woowacourse.shopping.ui.detailedProduct

import woowacourse.shopping.model.ProductUIModel

interface DetailedProductContract {
    interface View {
        fun setProductDetail(product: ProductUIModel, lastProduct: ProductUIModel?)
        fun navigateToCart()
        fun navigateToDetailedProduct(product: ProductUIModel)
    }

    interface Presenter {
        fun setUpProductDetail()
        fun addProductToCart()
        fun addProductToRecent()
        fun navigateToDetailedProduct()
    }
}
