package woowacourse.shopping.view.productlist

import woowacourse.shopping.domain.Product

interface ProductListContract {
    interface View {
        fun showProducts(products: List<Product>)
    }

    interface Presenter {
        fun fetchProducts()
    }
}
