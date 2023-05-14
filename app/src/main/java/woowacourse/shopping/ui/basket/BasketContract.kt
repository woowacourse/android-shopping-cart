package woowacourse.shopping.ui.basket

import woowacourse.shopping.model.PageNumber
import woowacourse.shopping.model.UiProduct

interface BasketContract {
    interface View {
        val presenter: Presenter

        fun updateBasket(products: List<UiProduct>)
        fun updateNavigatorEnabled(previous: Boolean, next: Boolean)
        fun closeScreen()
        fun updatePageNumber(page: PageNumber)
    }

    interface Presenter {
        val view: View

        fun fetchBasket()
        fun fetchPrevious()
        fun fetchNext()
        fun removeBasketProduct(product: UiProduct)
        fun closeScreen()
    }
}
