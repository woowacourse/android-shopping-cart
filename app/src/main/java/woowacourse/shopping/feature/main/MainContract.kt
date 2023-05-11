package woowacourse.shopping.feature.main

import com.example.domain.Product
import com.example.domain.RecentProducts

interface MainContract {

    interface View {
        fun setProducts(products: List<Product>, recentProducts: RecentProducts)
    }

    interface Presenter {
        fun loadProducts()
    }
}
