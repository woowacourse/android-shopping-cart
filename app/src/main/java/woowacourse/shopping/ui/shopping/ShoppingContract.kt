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

    interface Presenter {
        val view: View

        fun fetchAll()
        fun fetchProducts()
        fun fetchRecentProducts()
        fun inquiryProductDetail(product: UiProduct)
        fun inquiryRecentProductDetail(recentProduct: UiRecentProduct)
        fun openBasket()
    }
}
