package woowacourse.shopping.ui.basket

import woowacourse.shopping.ui.model.UiBasketProduct

interface BasketContract {
    interface View {
        val presenter: Presenter

        fun updateBasketProducts(products: List<UiBasketProduct>)

        fun updateNavigatorEnabled(previous: Boolean, next: Boolean)

        fun updateCurrentPage(currentPage: Int)
    }

    interface Presenter {
        val view: View
        var lastId: Int

        fun fetchPreviousBasketProducts(currentProducts: List<UiBasketProduct>)

        fun fetchBasketProducts(standard: Int = lastId, includeStandard: Boolean = false)

        fun removeBasketProduct(product: UiBasketProduct, currentProducts: List<UiBasketProduct>)

        fun updateCurrentPage(isIncrease: Boolean)
    }
}
