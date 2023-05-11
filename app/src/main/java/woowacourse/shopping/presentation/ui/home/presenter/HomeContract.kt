package woowacourse.shopping.presentation.ui.home.presenter

import woowacourse.shopping.domain.model.Product

interface HomeContract {
    interface View {
        val presenter: Presenter
        fun setProducts(products: List<Product>)
        fun setRecentlyViewed(products: List<Product>)
        fun getProductCount(): Int
    }

    interface Presenter {
        fun getProducts()
        fun getRecentlyViewed()
        fun getMoreProducts(productId: Long)
    }
}
