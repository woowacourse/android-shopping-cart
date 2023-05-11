package woowacourse.shopping.ui.basket

import woowacourse.shopping.ui.model.UiProduct

interface BasketContract {
    interface View {
        val presenter: Presenter

        fun updateBasketProducts(products: List<UiProduct>)
    }

    interface Presenter {
        val view: View

        fun fetchBasketProducts()

        fun removeBasketProduct(product: UiProduct)
    }
}
