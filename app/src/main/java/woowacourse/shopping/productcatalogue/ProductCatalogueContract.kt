package woowacourse.shopping.productcatalogue

import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

interface ProductCatalogueContract {
    interface View {
        fun notifyDataChanged()
        fun setRecentProductList(recentProducts: List<RecentProductUIModel>)
        fun updateProductList(recentProducts: List<RecentProductUIModel>)
        fun setCartCountCircle(count: Int)
    }

    interface Presenter {
        fun getRecentProduct()
        fun readMoreOnClick(unitSize: Int, page: Int)
        fun updateCartCount()
        fun getProductCount(product: ProductUIModel): Int
        fun deleteCartProduct(cartProduct: CartProductUIModel)
        fun decreaseCartProductCount(cartProduct: CartProductUIModel, count: Int)
        fun increaseCartProductCount(cartProduct: CartProductUIModel, count: Int)
    }
}
