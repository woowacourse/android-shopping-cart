package woowacourse.shopping.ui.products

import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

interface ProductListContract {
    interface Presenter {
        fun loadRecentlyViewedProducts()
        fun loadProducts(limit: Int, offset: Int)
        fun plusCount(productId: Long, oldCount: Int)
        fun minusCount(productId: Long, oldCount: Int)
        fun startCount(product: ProductUIState)
    }

    interface View {
        fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>)
        fun addProducts(products: List<ProductUIState>)
        fun updateCartItem(productId: Long, count: Int)
        fun deleteCartItem(productId: Long)
    }
}
