package woowacourse.shopping.presentation.ui.home

import woowacourse.shopping.presentation.model.HomeData
import woowacourse.shopping.presentation.model.RecentlyViewedProduct

interface HomeContract {
    interface View {
        val presenter: Presenter
        fun setHomeData(homeData: List<HomeData>)
        fun initRecentlyViewed()
        fun updateRecentlyViewedProducts(products: List<RecentlyViewedProduct>)
        fun appendProductItems(startPosition: Int, size: Int)
        fun appendShowMoreItem(position: Int)
        fun removeShowMoreItem(position: Int)
    }

    interface Presenter {
        fun setHome()
        fun fetchProducts()
        fun fetchRecentlyViewed()
    }
}
