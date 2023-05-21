package woowacourse.shopping.ui.basket

import woowacourse.shopping.model.PageNumber
import woowacourse.shopping.model.UiBasketProduct
import woowacourse.shopping.model.UiProduct

interface BasketContract {
    interface View {
        fun updateBasket(basketProducts: List<UiBasketProduct>)
        fun updateNavigatorEnabled(previousEnabled: Boolean, nextEnabled: Boolean)
        fun updatePageNumber(page: PageNumber)
        fun updateTotalPrice(totalPrice: Int)
        fun showOrderComplete(productCount: Int)
        fun showOrderFailed()
        fun navigateToHome()
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchBasket(page: Int)
        abstract fun changeProductCount(product: UiProduct, count: Int, increase: Boolean)
        abstract fun changeProductSelectState(product: UiProduct, isSelect: Boolean)
        abstract fun toggleAllCheckState()
        abstract fun removeProduct(product: UiProduct)
        abstract fun order()
        abstract fun navigateToHome()
    }
}
