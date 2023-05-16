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
        fun loadCartItems(currentPage: Int)
        fun deleteCartItem(currentPage: Int, itemId: Long)
        fun calculatePreviousPage(currentPage: Int): Int
        fun calculateNextPage(currentPage: Int): Int
    }
}
