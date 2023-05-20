package woowacourse.shopping.ui.productdetail.contract

import woowacourse.shopping.model.ProductUIModel

interface ProductDetailContract {
    interface View {
        fun setProductDetail(product: ProductUIModel)
        fun showProductCountDialog(product: ProductUIModel)

        fun showLatestProduct(product: ProductUIModel)
    }

    interface Presenter {
        fun setUpProductDetail()
        fun addProductToCart()
        fun addProductToRecent()

        fun addProductCount()
        fun subtractProductCount()

        fun setProductCountDialog()

        fun setLatestProduct()
    }
}
