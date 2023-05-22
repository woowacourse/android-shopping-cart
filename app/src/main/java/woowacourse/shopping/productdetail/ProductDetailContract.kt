package woowacourse.shopping.productdetail

import woowacourse.shopping.uimodel.ProductUIModel
import woowacourse.shopping.uimodel.RecentProductUIModel

interface ProductDetailContract {
    interface View {
        fun showCartPage()
        fun showRecentProduct(recentProduct: RecentProductUIModel)
        fun setLatestProductVisibility()
    }

    interface Presenter {
        val product: ProductUIModel
        fun insertRecentRepository(currentTime: Long)
        fun addToCart()
        fun getMostRecentProduct()
    }
}
