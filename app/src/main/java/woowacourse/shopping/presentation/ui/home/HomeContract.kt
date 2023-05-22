package woowacourse.shopping.presentation.ui.home

import woowacourse.shopping.domain.model.Operator
import woowacourse.shopping.domain.model.RecentlyViewedProduct
import woowacourse.shopping.presentation.model.HomeData

interface HomeContract {
    interface View {
        val presenter: Presenter
        fun setHomeData(homeData: List<HomeData>)
        fun initRecentlyViewed()
        fun updateRecentlyViewedProducts(products: List<RecentlyViewedProduct>)
        fun appendProductItems(startPosition: Int, size: Int)
        fun appendShowMoreItem(position: Int)
        fun removeShowMoreItem(position: Int)
        fun showUnexpectedError()
        fun updateProductQuantity(position: Int)
        fun updateTotalQuantity(size: Int)
    }

    interface Presenter {
        fun setHome()
        fun fetchProducts()
        fun fetchRecentlyViewed()
        fun updateProductQuantity(position: Int, operator: Operator)
        fun fetchTotalQuantity()
    }
}
