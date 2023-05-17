package woowacourse.shopping.presentation.ui.home

import woowacourse.shopping.domain.model.Product

interface HomeContract {
    interface View {
        val presenter: Presenter
        fun setProducts(products: List<Product>, isLastProduct: Boolean)
        fun setRecentlyViewed(products: List<Product>)
    }

    interface Presenter {
        fun getProducts()
        fun getRecentlyViewed()
    }
}
