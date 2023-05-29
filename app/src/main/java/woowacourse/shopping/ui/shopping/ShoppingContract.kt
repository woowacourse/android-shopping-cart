package woowacourse.shopping.ui.shopping

import woowacourse.shopping.ui.model.ProductModel
import woowacourse.shopping.ui.model.RecentProductModel
import woowacourse.shopping.ui.model.ShoppingProductModel

interface ShoppingContract {
    interface Presenter {
        fun updateChange(difference: List<ShoppingProductModel>)

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

        fun updateChange(difference: List<ShoppingProductModel>)
    }
}
