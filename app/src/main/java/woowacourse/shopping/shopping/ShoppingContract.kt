package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.ProductModel
import woowacourse.shopping.common.model.RecentProductModel
import woowacourse.shopping.common.model.ShoppingProductModel

interface ShoppingContract {
    interface Presenter {
        fun updateCartChange()

        fun updateRecentProducts()

        fun setUpCartAmount()

        fun openProduct(productModel: ProductModel)

        fun openCart()

        fun loadMoreProduct()

        fun decreaseCartProductAmount(shoppingProductModel: ShoppingProductModel)

        fun increaseCartProductAmount(shoppingProductModel: ShoppingProductModel)
    }

    interface View {
        fun updateProducts(productModels: List<ShoppingProductModel>)

        fun addProducts(productModels: List<ShoppingProductModel>)

        fun updateRecentProducts(recentProductModels: List<RecentProductModel>)

        fun showProductDetail(productModel: ProductModel, recentProductModel: ProductModel?)

        fun showCart()

        fun updateCartAmount(amount: Int)

        fun updateShoppingProduct(prev: ShoppingProductModel, new: ShoppingProductModel)
    }
}
