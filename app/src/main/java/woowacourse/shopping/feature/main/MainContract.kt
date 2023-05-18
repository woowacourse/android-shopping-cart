package woowacourse.shopping.feature.main

import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel

interface MainContract {
    interface View {
        fun showCartScreen()
        fun showProductDetailScreenByProduct(product: ProductUiModel)
        fun addProducts(products: List<ProductUiModel>)
        fun updateRecent(recent: List<RecentProductUiModel>)
        fun showProductDetailScreenByRecent(recentProduct: RecentProductUiModel)
    }

    interface Presenter {
        fun loadProducts()
        fun moveToCart()
        fun moveToDetail(product: ProductUiModel)
        fun loadMoreProduct()
        fun loadRecent()
        fun refresh()
        fun increaseCartProduct(product: ProductUiModel, previousCount: Int)
        fun decreaseCartProduct(product: ProductUiModel, previousCount: Int)
    }
}
