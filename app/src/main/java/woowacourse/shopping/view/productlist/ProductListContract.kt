package woowacourse.shopping.view.productlist

import woowacourse.shopping.model.ProductModel

interface ProductListContract {
    interface View {
        fun showProducts(items: List<ProductListViewItem>)
        fun notifyAddProducts(position: Int, size: Int)

        fun notifyRecentViewedChanged()
    }

    interface Presenter {
        fun fetchProducts()
        fun showMoreProducts()
        fun updateRecentViewed(id: Int)
    }
}
