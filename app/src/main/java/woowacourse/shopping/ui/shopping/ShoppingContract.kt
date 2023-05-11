package woowacourse.shopping.ui.shopping

import woowacourse.shopping.ui.model.UiProduct

interface ShoppingContract {
    interface View {
        val presenter: Presenter

        fun updateProducts(products: List<UiProduct>)
        fun updateRecentProducts(recentProducts: List<UiProduct>)

        fun showProductDetail(product: UiProduct)
    }

    interface Presenter {
        val view: View

        fun fetchProducts()
        fun fetchRecentProducts()

        fun inquiryRecentProduct(product: UiProduct)
    }
}
