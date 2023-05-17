package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.RecentProductModel

interface ShoppingContract {
    interface Presenter {
        fun reloadProducts()

        fun openProduct(cartProductModel: CartProductModel)

        fun openCart()

        fun loadMoreProduct()
    }

    interface View {
        fun updateProducts(cartProductModels: List<CartProductModel>)

        fun addProducts(cartProductModels: List<CartProductModel>)

        fun updateRecentProducts(recentProductModels: List<RecentProductModel>)

        fun showProductDetail(cartProductModel: CartProductModel)

        fun showCart()
    }
}
