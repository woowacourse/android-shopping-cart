package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.ProductUIModel
import woowacourse.shopping.model.RecentProductUIModel

interface ShoppingContract {
    interface View {
        fun addMoreProducts(products: List<ProductUIModel>)
        fun setRecentProducts(recentProductsData: List<RecentProductUIModel>)
        fun setCartProducts(cartCounts: Map<Int, Int>)
        fun updateToolbar(totalCount: Int)
        fun navigateToProductDetail(product: ProductUIModel)
    }

    interface Presenter {
        fun setUpProducts()
        fun fetchCartProducts()
        fun fetchRecentProducts()
        fun fetchNextProducts()
        fun updateItemCount(productId: Int, count: Int): Int
        fun fetchTotalCount()
        fun navigateToItemDetail(productId: Int)
    }
}
