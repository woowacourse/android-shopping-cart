package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.UiProduct
import woowacourse.shopping.model.UiRecentProduct

interface ShoppingContract {
    interface View {
        val presenter: Presenter

        fun updateProducts(products: List<UiProduct>)
        fun updateRecentProducts(recentProducts: List<UiRecentProduct>)
        fun showProductDetail(product: UiProduct)
        fun navigateToBasketScreen()
        fun showLoadMoreButton()
        fun hideLoadMoreButton()
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchAll()
        abstract fun fetchProducts()
        abstract fun fetchRecentProducts()
        abstract fun inquiryProductDetail(product: UiProduct)
        abstract fun inquiryRecentProductDetail(recentProduct: UiRecentProduct)
        abstract fun openBasket()
    }
}
