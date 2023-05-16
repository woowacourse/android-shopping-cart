package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.presentation.model.CartModel

interface CartContract {
    interface View {
        fun setCartItemsView(carts: List<CartModel>, currentPage: String)
        fun setEnableLeftButton(isEnabled: Boolean)
        fun setEnableRightButton(isEnabled: Boolean)
    }

    interface Presenter {
        fun loadCartItems()
        fun deleteCartItem(itemId: Long)
        fun decrementPage()
        fun incrementPage()
    }
}
