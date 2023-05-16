package woowacourse.shopping.ui.basket

import woowacourse.shopping.model.PageNumber
import woowacourse.shopping.model.UiProduct

interface BasketContract {
    interface View {
        val presenter: Presenter

        fun updateBasket(products: List<UiProduct>)
        fun updateNavigatorEnabled(previousEnabled: Boolean, nextEnabled: Boolean)
        fun closeScreen()
        fun updatePageNumber(page: PageNumber)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchBasket()
        abstract fun fetchPrevious()
        abstract fun fetchNext()
        abstract fun removeBasketProduct(product: UiProduct)
        abstract fun closeScreen()
    }
}
