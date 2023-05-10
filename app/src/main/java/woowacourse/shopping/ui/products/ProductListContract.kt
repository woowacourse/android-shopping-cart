package woowacourse.shopping.ui.products

interface ProductListContract {
    interface Presenter {
        fun loadRecentlyViewedProducts()
        fun loadProducts()
        fun addRecentlyViewedProduct(productId: Long)
    }

    interface View {
        fun setRecentlyViewedProducts(recentlyViewedProducts: List<RecentlyViewedProductUIState>)
        fun setProducts(products: List<ProductUIState>)
    }
}
