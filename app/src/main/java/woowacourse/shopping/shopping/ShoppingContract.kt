package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.RecentProductModel

interface ShoppingContract {
    interface Presenter {
        fun resumeView()

        fun openProduct(productModel: ProductModel)

        fun openCart()
    }

    interface View {
        fun updateProductList(productModels: List<ProductModel>)

        fun updateRecentProductList(recentProductModels: List<RecentProductModel>)

        fun showProductDetail(productModel: ProductModel)

        fun showCart()
    }
}
