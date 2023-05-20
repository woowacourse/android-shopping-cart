package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.BasketProduct
import woowacourse.shopping.model.ProductCount
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

interface ShoppingContract {
    interface View {
        fun updateProducts(products: List<BasketProduct>)
        fun updateRecentProducts(recentProducts: List<UiRecentProduct>)
        fun navigateToProductDetail(product: UiProduct, recentProduct: UiRecentProduct?)
        fun navigateToBasket()
        fun showLoadMoreButton()
        fun hideLoadMoreButton()
        fun updateBasketProductBadge(count: ProductCount)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchAll()
        abstract fun fetchProducts()
        abstract fun fetchRecentProducts()
        abstract fun loadMoreProducts()
        abstract fun inquiryProductDetail(product: UiProduct)
        abstract fun inquiryRecentProductDetail(recentProduct: UiRecentProduct)
        abstract fun navigateToBasket()
        abstract fun increaseCartCount(product: UiProduct, count: Int = 1)
        abstract fun decreaseCartCount(product: UiProduct, count: Int = 1)
    }
}
