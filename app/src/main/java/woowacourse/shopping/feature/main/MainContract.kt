package woowacourse.shopping.feature.main

import woowacourse.shopping.feature.main.product.MainProductItemModel
import woowacourse.shopping.feature.main.recent.RecentProductItemModel
import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel

interface MainContract {
    interface View {
        fun showCartScreen()
        fun showProductDetailScreen(productUiModel: ProductUiModel)
        fun addProducts(products: List<ProductUiModel>)
        fun updateRecent(recent: List<RecentProductUiModel>)
    }

    interface Presenter {
        fun loadProducts()
        fun moveToCart()
        fun loadMoreProduct()
        fun loadRecent()
        fun showProductDetail(position: Int)
        fun showRecentProductDetail(position: Int)
        fun resetProducts()
    }
}
