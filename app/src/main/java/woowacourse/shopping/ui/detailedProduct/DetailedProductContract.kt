package woowacourse.shopping.ui.detailedProduct

import woowacourse.shopping.model.ProductUIModel

interface DetailedProductContract {
    interface View {
        fun setProductDetail(product: ProductUIModel, lastProduct: ProductUIModel?)
        fun navigateToCart()
        fun navigateToDetailedProduct(product: ProductUIModel)
        fun navigateToAddToCartDialog(product: ProductUIModel)
    }

    interface Presenter {
        fun setUpLastProduct()
        fun setUpProductDetail()
        fun addProductToCart(count: Int)
        fun addProductToRecent()
        fun navigateToDetailedProduct()
        fun navigateToAddToCartDialog()
    }
}
