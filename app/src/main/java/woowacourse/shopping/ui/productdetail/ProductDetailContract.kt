package woowacourse.shopping.ui.productdetail

import woowacourse.shopping.ui.productdetail.uistate.ProductDetailUIState
import woowacourse.shopping.ui.products.uistate.RecentlyViewedProductUIState

interface ProductDetailContract {
    interface Presenter {
        fun loadProduct(productId: Long)
        fun addProductToCart(productId: Long, count: Int)
        fun addRecentlyViewedProduct(productId: Long)
        fun showLastlyViewedProduct(productId: Long)
    }

    interface View {
        fun setProduct(product: ProductDetailUIState)
        fun showLastlyViewedProduct(product: RecentlyViewedProductUIState)
        fun hideLastlyViewedProduct()
        fun showErrorMessage()
    }
}
