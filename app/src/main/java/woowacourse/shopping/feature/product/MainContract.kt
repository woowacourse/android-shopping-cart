package woowacourse.shopping.feature.product

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.model.ProductState

interface MainContract {

    interface View {
        fun setProducts(products: List<Product>)
        fun setRecentProducts(recentProducts: List<RecentProduct>)
        fun addProductItems(products: List<ProductState>)
        fun showProductDetail(productState: ProductState)
        fun showEmptyProducts()
    }

    interface Presenter {
        fun loadRecentProducts()
        fun loadMoreProducts()
        fun addRecentProduct(product: Product)
        fun showProductDetail(productState: ProductState)
    }
}
