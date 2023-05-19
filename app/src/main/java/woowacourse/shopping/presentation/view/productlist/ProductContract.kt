package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel

interface ProductContract {
    interface View {
        fun setProductItemsView(products: List<ProductModel>)
        fun setRecentProductItemsView(recentProducts: List<RecentProductModel>)
        fun updateRecentProductItemsView()
        fun updateMoreProductsView(newProducts: List<ProductModel>)
    }

    interface Presenter {
        fun deleteNotTodayRecentProducts()
        fun loadProductItems()
        fun loadRecentProductItems()
        fun updateRecentProductItems()
        fun saveRecentProduct(productId: Long)
        fun loadMoreData(startPosition: Int)
        fun updateCartProduct(productId: Long, count: Int)
    }
}
