package woowacourse.shopping.ui.products

import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

interface ProductListContract {
    interface Presenter {
        fun onLoadNextPage()
        fun loadRecentlyViewedProducts()
        fun addRecentlyViewedProduct(productId: Long)
    }

    interface View {
        fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>)
        fun addProducts(products: List<ProductUIState>)
    }
}
