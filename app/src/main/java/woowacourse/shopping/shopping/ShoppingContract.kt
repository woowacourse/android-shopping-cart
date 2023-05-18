package woowacourse.shopping.shopping

import woowacourse.shopping.common.model.CartProductModel
import woowacourse.shopping.common.model.ProductModel
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
        fun updateProducts(cartProducts: List<CartProductModel>)
        fun addProducts(cartProducts: List<CartProductModel>)
        fun updateRecentProducts(recentProducts: List<RecentProductModel>)
        fun showProductDetail(cartProduct: CartProductModel, recentProduct: ProductModel?)
        fun showCart()
        fun updateCartProductsCount(countOfProduct: Int)
    }
}
