package woowacourse.shopping.ui.basket

import woowacourse.shopping.model.UiProduct

interface BasketContract {
    interface View {
        val presenter: Presenter

        fun updateBasket(products: List<UiProduct>)
        fun updateNavigatorEnabled(previous: Boolean, next: Boolean)
        fun closeScreen()
    }

    interface Presenter {
        val view: View

        fun fetchBasket(isNext: Boolean)
        fun removeBasketProduct(product: UiProduct)
        fun closeScreen()
    }
}
