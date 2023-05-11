package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.RecentProductModel

interface ShoppingContract {
    interface Presenter {
        fun resumeView()

        fun openProduct(productModel: ProductModel)

        fun openCart()

        fun loadMoreProduct()
    }

    interface View {
        fun updateProducts(productModels: List<ProductModel>)

        fun addProducts(productModels: List<ProductModel>)

        fun updateRecentProducts(recentProductModels: List<RecentProductModel>)

        fun showProductDetail(productModel: ProductModel)

        fun showCart()
    }
}
