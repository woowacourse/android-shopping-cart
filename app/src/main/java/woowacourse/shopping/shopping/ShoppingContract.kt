package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.RecentProductModel

interface ShoppingContract {
    interface Presenter {
        fun reloadProducts()

        fun openProduct(cartProduct: CartProductModel)

        fun openCart()

        fun loadMoreProduct()

        fun minusCartProduct(cartProduct: CartProductModel)

        fun plusCartProduct(cartProduct: CartProductModel)
    }

    interface View {
        fun updateProducts(cartProductModels: List<CartProductModel>)

        fun addProducts(cartProductModels: List<CartProductModel>)

        fun updateRecentProducts(recentProductModels: List<RecentProductModel>)

        fun showProductDetail(cartProductModel: CartProductModel)

        fun showCart()
    }
}
