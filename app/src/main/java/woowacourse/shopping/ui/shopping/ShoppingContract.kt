package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.BasketProduct
import woowacourse.shopping.model.ProductCount
import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

interface ShoppingContract {
    interface View {
        val presenter: Presenter

        fun updateProducts(products: List<BasketProduct>)
        fun updateRecentProducts(recentProducts: List<UiRecentProduct>)
        fun showProductDetail(product: UiProduct, recentProduct: UiRecentProduct?)
        fun navigateToBasketScreen()
        fun showLoadMoreButton()
        fun hideLoadMoreButton()
        fun updateBasketProductCount(count: ProductCount)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchAll()
        abstract fun fetchProducts()
        abstract fun fetchRecentProducts()
        abstract fun inquiryProductDetail(product: UiProduct)
        abstract fun inquiryRecentProductDetail(recentProduct: UiRecentProduct)
        abstract fun openBasket()
        abstract fun addBasketProduct(product: UiProduct, count: Int = 1)
        abstract fun removeBasketProduct(product: UiProduct)
    }
}
