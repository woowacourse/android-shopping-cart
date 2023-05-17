package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel

interface ProductContract {
    interface View {
        fun setProductItemsView(products: List<ProductModel>)
        fun setRecentProductItemsView(recentProducts: List<RecentProductModel>)
        fun updateRecentProductItemsView(preSize: Int, diffSize: Int)
        fun updateMoreProductsView(preSize: Int, diffSize: Int)
        fun moveToCartView()
    }

    interface Presenter {
        fun initRecentProductItems()
        fun loadProductItems()
        fun loadRecentProductItems()
        fun updateRecentProductItems()
        fun saveRecentProduct(productId: Long)
        fun loadMoreData()
        fun actionOptionItem(itemId: Int)
        fun getRecentProductsLastScroll(): Int
        fun updateRecentProductsLastScroll(lastScroll: Int)
    }
}
