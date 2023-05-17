package woowacourse.shopping.view.productlist

import woowacourse.shopping.model.ProductModel

interface ProductListContract {
    interface View {
        fun showProducts(items: List<ProductListViewItem>)
        fun showCartCount(count: Int)
        fun onClickProductDetail(product: ProductModel, lastViewedProduct: ProductModel)
        fun notifyAddProducts(position: Int, size: Int)
        fun notifyRecentViewedChanged()
    }

    interface Presenter {
        fun fetchProducts()
        fun showProductDetail(product: ProductModel)
        fun showMoreProducts()
        fun updateRecentViewed(id: Int)
        fun addToCartProducts(id: Int, count: Int)
        fun updateCartProductCount(id: Int, count: Int)
        fun fetchCartCount()
    }
}
