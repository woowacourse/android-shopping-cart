package woowacourse.shopping.presentation.view.productlist

import woowacourse.shopping.presentation.model.ProductModel
import woowacourse.shopping.presentation.model.RecentProductModel

interface ProductContract {
    interface View {
        fun setProductItemsView(products: List<ProductModel>)
        fun setRecentProductItemsView(recentProducts: List<RecentProductModel>)
    }
    interface Presenter {
        fun loadProductItems()
        fun loadRecentProductItems()
        fun saveRecentProduct(productId: Long)
    }
}
