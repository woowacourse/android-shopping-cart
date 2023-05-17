package woowacourse.shopping.view.productlist

interface ProductListContract {
    interface View {
        fun showProducts(items: List<ProductListViewItem>)
        fun showCartCount(count: Int)
        fun notifyAddProducts(position: Int, size: Int)
        fun notifyRecentViewedChanged()
    }

    interface Presenter {
        fun fetchProducts()
        fun showMoreProducts()
        fun updateRecentViewed(id: Int)
        fun addToCartProducts(id: Int, count: Int)
        fun updateCartProductCount(id: Int, count: Int)
        fun fetchCartCount()
    }
}
