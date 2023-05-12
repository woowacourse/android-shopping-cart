package woowacourse.shopping.feature.main

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.feature.list.item.RecentProductListItem

interface MainContract {

    interface View {
        fun setProducts(products: List<Product>, recentProducts: List<RecentProduct>)
        fun addProducts(products: List<Product>)
    }

    interface Presenter {
        fun addProducts()
        fun storeRecentProduct(recentProductListItem: RecentProductListItem)
    }
}
