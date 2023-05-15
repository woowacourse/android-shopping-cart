package woowacourse.shopping.feature.main

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.list.item.ListItem
import woowacourse.shopping.list.item.ProductListItem
import woowacourse.shopping.model.ProductState

interface MainContract {

    interface View {
        fun setProducts(products: List<Product>, recentProducts: List<RecentProduct>)
        fun addProductItems(products: List<ProductListItem>)
        fun showProductDetail(productState: ProductState)
        fun showEmptyProducts()
    }

    interface Presenter {
        fun loadMoreProducts()
        fun showProductDetail(listItem: ListItem)
    }
}
