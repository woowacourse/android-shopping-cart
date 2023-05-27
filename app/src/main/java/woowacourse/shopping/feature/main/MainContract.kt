package woowacourse.shopping.feature.main

import woowacourse.shopping.model.ProductUiModel
import woowacourse.shopping.model.RecentProductUiModel

interface MainContract {
    interface View {
        fun showCartScreen()
        fun showProductDetailScreenByProduct(product: ProductUiModel, recentProduct: ProductUiModel?)
        fun updateRecent(recent: List<RecentProductUiModel>)
        fun showProductDetailScreenByRecent(recentProduct: RecentProductUiModel)
        fun updateCartProductCount(count: Int)
        fun submitList(products: List<ProductUiModel>)
    }

    interface Presenter {
        fun loadProducts()
        fun loadRecent()
        fun setCartProductCount()
        fun moveToCart()
        fun moveToDetail(product: ProductUiModel)
        fun refresh()
        fun increaseCartProduct(product: ProductUiModel, previousCount: Int)
        fun decreaseCartProduct(product: ProductUiModel, previousCount: Int)
        fun updateProducts()
    }
}
