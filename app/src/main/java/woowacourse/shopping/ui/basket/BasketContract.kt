package woowacourse.shopping.ui.basket

import woowacourse.shopping.ui.model.UiBasketProduct

interface BasketContract {
    interface View {
        val presenter: Presenter

        fun updateBasketProducts(products: List<UiBasketProduct>)

        fun updateNavigatorEnabled(previous: Boolean, next: Boolean)
    }

    interface Presenter {
        val view: View

        fun fetchBasketProducts(isNext: Boolean = true)

        fun removeBasketProduct(product: UiBasketProduct)
    }
}
