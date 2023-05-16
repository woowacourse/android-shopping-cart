package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.presentation.model.CartModel

interface CartContract {
    interface View {
        fun setCartItemsView(carts: List<CartModel>)
        fun setEnableLeftButton(isEnabled: Boolean)
        fun setEnableRightButton(isEnabled: Boolean)
        fun setPageCountView(page: Int)
    }

    interface Presenter {
        fun loadCartItems()
        fun deleteCartItem(itemId: Long)
        fun calculatePreviousPage()
        fun calculateNextPage()
    }
}
