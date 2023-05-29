package woowacourse.shopping.productdetail

import woowacourse.shopping.uimodel.CartProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

interface ProductDetailContract {
    interface View {
        fun showCartPage()
        fun showRecentProduct(recentProduct: RecentProductUIModel)
        fun setLatestProductVisibility()
        fun setCartProductData(cartProduct: CartProductUIModel)
        fun navigateToAddToCartDialog(cartProduct: CartProductUIModel)
    }

    interface Presenter {
        fun insertRecentRepository(currentTime: Long)
        fun getMostRecentProduct()
        fun addToCart(count: Int)
        fun attachCartProductData()
    }
}
