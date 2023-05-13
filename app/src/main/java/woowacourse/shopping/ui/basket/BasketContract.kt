package woowacourse.shopping.ui.basket

import woowacourse.shopping.model.UiProduct

interface BasketContract {
    interface View {
        val presenter: Presenter

        fun updateBasketProducts(products: List<UiProduct>)
        fun updateNavigatorEnabled(previous: Boolean, next: Boolean)
    }

    interface Presenter {
        val view: View

        fun fetchBasketProducts(isNext: Boolean = true)

        fun removeBasketProduct(product: UiProduct)
    }
}
