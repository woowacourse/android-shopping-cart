package woowacourse.shopping.feature.main

import com.example.domain.Product

interface MainContract {

    interface View {
        fun setProducts(products: List<Product>)
    }

    interface Presenter {
        fun loadProducts()
    }
}
