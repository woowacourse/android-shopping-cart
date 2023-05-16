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
        var lastId: Int

        fun fetchBasketProducts(isNext: Boolean, startId: Int = lastId)

        fun removeBasketProduct(product: UiBasketProduct, currentProducts: List<UiBasketProduct>)
    }
}
