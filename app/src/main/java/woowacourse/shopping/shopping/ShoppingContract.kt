package woowacourse.shopping.shopping

import woowacourse.shopping.productdetail.ProductUiModel

interface ShoppingContract {

    interface View {

        fun setUpShoppingView(products: List<ProductUiModel>, recentViewedProducts: List<ProductUiModel>)
    }

    interface Presenter {

        fun loadProducts()

        fun addToRecentViewedProduct(id: Int)
    }
}
