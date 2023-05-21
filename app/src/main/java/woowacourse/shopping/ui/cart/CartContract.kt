package woowacourse.shopping.ui.cart

import woowacourse.shopping.model.PageNumber
import woowacourse.shopping.model.UiCartProduct
import woowacourse.shopping.model.UiProduct

interface CartContract {
    interface View {
        fun updateCart(cartProducts: List<UiCartProduct>)
        fun updateNavigatorEnabled(previousEnabled: Boolean, nextEnabled: Boolean)
        fun updatePageNumber(page: PageNumber)
        fun updateTotalPrice(totalPrice: Int)
        fun showOrderComplete(productCount: Int)
        fun showOrderFailed()
        fun navigateToHome()
    }

    abstract class Presenter(protected val view: View) {
        abstract fun fetchCart(page: Int)
        abstract fun changeProductCount(product: UiProduct, count: Int, increase: Boolean)
        abstract fun changeProductSelectState(product: UiProduct, isSelect: Boolean)
        abstract fun toggleAllCheckState()
        abstract fun removeProduct(product: UiProduct)
        abstract fun order()
        abstract fun navigateToHome()
    }
}
