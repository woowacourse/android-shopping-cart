package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.model.ProductModel

interface ProductDetailContract {
    interface Presenter {
        fun setupCartProductDialog()

        fun openProduct(productModel: ProductModel)
    }

    interface View {
        fun setupProductDetail(productModel: ProductModel)

        fun setupRecentProductDetail(recentProductModel: ProductModel?)

        fun showCartProductDialog(productModel: ProductModel)

        fun showProductDetail(productModel: ProductModel, recentProductModel: ProductModel?)
    }
}
