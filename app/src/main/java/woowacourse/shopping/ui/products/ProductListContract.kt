package woowacourse.shopping.ui.products

import woowacourse.shopping.ui.products.uistate.ProductUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

interface ProductListContract {
    interface Presenter {
        fun getCurrentPage(): Int
        fun restoreCurrentPage(currentPage: Int)
        fun onLoadRecentlyViewedProducts()
        fun onLoadProductsNextPage()
        fun onAddToCart(productId: Long)
        fun onRefreshProducts()
    }

    interface View {
        fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>)
        fun addProducts(products: List<ProductUIState>)
        fun replaceProduct(product: ProductUIState)
        fun setProducts(products: List<ProductUIState>)
        fun setCanLoadMore(canLoadMore: Boolean)
    }
}
