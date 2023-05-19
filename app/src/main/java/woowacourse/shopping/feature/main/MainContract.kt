package woowacourse.shopping.feature.main

import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel

interface MainContract {
    interface View {
        fun showCartScreen()
        fun showProductDetailScreen(
            productUiModel: ProductUiModel,
            recentProductUiModel: RecentProductUiModel?
        )
        fun setProducts(products: List<ProductUiModel>)
        fun updateRecent(recent: List<RecentProductUiModel>)
        fun showCartCountBadge()
        fun hideCartCountBadge()
        fun updateCartCount(count: Int)
        fun hideLoadMore()
    }

    interface Presenter {
        fun loadProducts()
        fun loadRecent()
        fun loadCartCountSize()
        fun moveToCart()
        fun showProductDetail(productId: Long)
        fun showRecentProductDetail(productId: Long)
        fun changeProductCartCount(productId: Long, count: Int)
        fun loadMoreProduct()
        fun resetProducts()
    }
}
