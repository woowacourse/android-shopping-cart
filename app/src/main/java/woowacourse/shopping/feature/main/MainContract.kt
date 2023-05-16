package woowacourse.shopping.feature.main

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.item.ProductListItem
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.RecentProductState

interface MainContract {

    interface View {
        fun setProducts(products: List<Product>)
        fun setRecentProducts(recentProducts: List<RecentProduct>)
        fun addProductItems(products: List<ProductListItem>)
        fun addRecentProductItems(recentProducts: List<RecentProductState>)
        fun showProductDetail(productState: ProductState)
        fun showEmptyProducts()
    }

    interface Presenter {
        fun loadRecentProducts()
        fun loadMoreProducts()
        fun addRecentProduct(product: Product)
        fun showProductDetail(listItem: ListItem)
    }
}
