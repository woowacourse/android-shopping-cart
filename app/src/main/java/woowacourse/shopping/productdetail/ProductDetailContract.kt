package woowacourse.shopping.productdetail

import woowacourse.shopping.common.model.ProductModel

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
