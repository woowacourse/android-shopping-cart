package woowacourse.shopping.feature.product

import com.example.domain.Product
import com.example.domain.RecentProduct
import woowacourse.shopping.model.ProductState
import woowacourse.shopping.model.RecentProductState

interface MainContract {

    interface View {
        fun setProducts(products: List<Product>)
        fun setRecentProducts(recentProducts: List<RecentProduct>)
        fun setCartSize(count: Int)
        fun addProductItems(products: List<ProductState>)
        fun showProductDetail(productState: ProductState, recentProductState: RecentProductState?)
        fun showEmptyProducts()
        fun showCartSizeBadge()
        fun hideCartSizeBadge()
    }

    interface Presenter {
        fun loadRecentProducts()
        fun loadMoreProducts()
        fun loadCartSize()
        fun loadCart()
        fun addRecentProduct(product: Product)
        fun showProductDetail(productState: ProductState)
        fun storeCartProduct(productState: ProductState)
        fun minusCartProductCount(productState: ProductState)
        fun plusCartProductCount(productState: ProductState)
    }
}
