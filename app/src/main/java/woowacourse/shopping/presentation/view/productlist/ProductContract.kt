package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel

interface ProductContract {
    interface View {
        fun setProductItemsView(products: List<ProductModel>)
        fun setRecentProductItemsView(recentProducts: List<RecentProductModel>)
        fun setVisibleToolbarCartCountView()
        fun setGoneToolbarCartCountView()
        fun updateRecentProductItemsView(recentProducts: List<RecentProductModel>)
        fun updateToolbarCartCountView(count: Int)
        fun moveToCartView()
    }

    interface Presenter {
        fun initRecentProductItems()
        fun loadProductItems()
        fun loadRecentProductItems()
        fun loadCartItems()
        fun updateRecentProductItems()
        fun saveRecentProduct(productId: Long)
        fun actionOptionItem()
        fun getLastRecentProductItem(lastRecentIndex: Int): RecentProductModel
        fun getRecentProductsLastScroll(): Int
        fun updateRecentProductsLastScroll(lastScroll: Int)
        fun updateCount(productId: Long, count: Int)
    }
}
