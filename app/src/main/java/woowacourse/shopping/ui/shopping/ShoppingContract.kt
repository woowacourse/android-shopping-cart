package woowacourse.shopping.ui.shopping

import woowacourse.shopping.ui.model.UiProduct

interface ShoppingContract {
    interface View {
        val presenter: Presenter

        fun updateRecentProducts(recentProducts: List<UiProduct>)
    }

    interface Presenter {
        val view: View

        fun fetchRecentProducts()
    }
}
