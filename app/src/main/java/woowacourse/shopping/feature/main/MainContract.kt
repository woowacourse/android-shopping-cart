package woowacourse.shopping.feature.main

import com.example.domain.Product
import woowacourse.shopping.feature.list.item.ProductView

interface MainContract {

    interface View {
        fun setProducts(products: List<ProductView.ProductItem>, recentProducts: ProductView.RecentProductsItem)
        fun addProducts(products: List<Product>)
    }

    interface Presenter {
        fun addProducts()
        fun storeRecentProduct(product: ProductView.ProductItem)
    }
}
