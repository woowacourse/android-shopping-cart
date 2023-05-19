package woowacourse.shopping.ui.basket

import woowacourse.shopping.model.PageNumber
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct

interface BasketContract {
    interface View {
        val presenter: Presenter

        fun updateBasket(basketProducts: List<UiBasketProduct>)
        fun updateNavigatorEnabled(previousEnabled: Boolean, nextEnabled: Boolean)
        fun closeScreen()
        fun updatePageNumber(page: PageNumber)
        fun updateTotalPrice(price: Int)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchBasket(page: Int)
        abstract fun deleteBasketProduct(basketProduct: UiBasketProduct)
        abstract fun closeScreen()
        abstract fun decreaseProductCount(product: UiProduct)
        abstract fun increaseProductCount(product: UiProduct)
    }
}
