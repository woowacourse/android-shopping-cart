package woowacourse.shopping.ui.basket

import woowacourse.shopping.model.PageNumber
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct

interface BasketContract {
    interface View {
        fun updateBasket(basketProducts: List<UiBasketProduct>)
        fun updateNavigatorEnabled(previousEnabled: Boolean, nextEnabled: Boolean)
        fun navigateToHome()
        fun updatePageNumber(page: PageNumber)
        fun showOrderFailed()
        fun showOrderComplete(productCount: Int)
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchBasket(page: Int)
        abstract fun changeProductCount(product: UiProduct, count: Int, increase: Boolean)
        abstract fun changeProductSelectState(product: UiProduct, checked: Boolean)
        abstract fun removeFromCart(product: UiProduct)
        abstract fun closeScreen()
    }
}
