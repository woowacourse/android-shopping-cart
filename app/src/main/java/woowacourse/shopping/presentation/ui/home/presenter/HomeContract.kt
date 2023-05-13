package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.domain.model.Product

interface HomeContract {
    interface View {
        val presenter: Presenter
        fun setUpProducts(products: List<Product>)
        fun setUpRecentlyViewed(products: List<Product>)
        fun getProductCount(): Int
    }

    interface Presenter {
        fun fetchProducts()
        fun fetchRecentlyViewed()
        fun fetchMoreProducts(productId: Long)
    }
}
