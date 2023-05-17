package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.common.model.ShoppingProductModel

interface ShoppingContract {
    interface Presenter {
        fun updateRecentProducts()

        fun openProduct(productModel: ProductModel)

        fun openCart()

        fun loadMoreProduct()
    }

    interface View {
        fun addProducts(productModels: List<ShoppingProductModel>)

        fun updateRecentProducts(recentProductModels: List<RecentProductModel>)

        fun showProductDetail(productModel: ProductModel)

        fun showCart()
    }
}
