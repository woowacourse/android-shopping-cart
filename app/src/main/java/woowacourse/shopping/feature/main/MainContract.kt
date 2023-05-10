package woowacourse.shopping.feature.main

import woowacourse.shopping.feature.main.product.MainProductItemModel
import woowacourse.shopping.feature.main.recent.RecentProductItemModel

interface MainContract {
    interface View {
        fun showCartScreen()
        fun showProductDetailScreen(position: Int)
        fun addProducts(products: List<MainProductItemModel>)
        fun updateRecent(recent: List<RecentProductItemModel>)
    }

    interface Presenter {
        fun loadProducts()
        fun moveToCart()
        fun loadMore(lastProductId: Long)
        fun loadRecent()
    }
}
