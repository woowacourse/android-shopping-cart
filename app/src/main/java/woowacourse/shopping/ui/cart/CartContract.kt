package woowacourse.shopping.ui.cart

import woowacourse.shopping.ui.cart.uistate.CartUIState

interface CartContract {
    interface Presenter {
        fun loadCartItemsOfCurrentPage()
        fun deleteCartItem(productId: Long)
        fun setPageButtons()
        fun goLeftPage()
        fun goRightPage()
        fun minusItemCount(productId: Long, oldCount: Int)
        fun plusItemCount(productId: Long, oldCount: Int)
        fun updateCheckbox(isChecked: Boolean, item: CartUIState)
        fun setTotalItemsStateAtOnce(isChecked: Boolean)
    }

    interface View {
        fun setCartItems(cartItems: List<CartUIState>)
        fun initPageButtonClickListener()
        fun updateLeftButtonsEnabled(isEnabled: Boolean)
        fun updateRightButtonsEnabled(isEnabled: Boolean)
        fun updatePage(page: Int)
        fun updateTotalPrice(price: Int)
        fun updateTotalPurchaseButton(amount: Int)
        fun updateTotalCheckbox(isAllChecked: Boolean)
    }
}
