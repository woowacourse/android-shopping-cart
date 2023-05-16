package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.presentation.model.CartModel

interface CartContract {
    interface View {
        fun setCartItemsView(carts: List<CartModel>, currentPage: String)
        fun setEnableLeftButton(isEnabled: Boolean)
        fun setEnableRightButton(isEnabled: Boolean)
    }

    interface Presenter {
        fun loadCartItems(currentPage: String)
        fun deleteCartItem(currentPage: String, itemId: Long)
        fun decrementPage(currentPage: String)
        fun incrementPage(currentPage: String)
    }
}
