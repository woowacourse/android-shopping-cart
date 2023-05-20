package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.presentation.model.CartProductModel

interface CartContract {
    interface View {
        fun setCartItemsView(carts: List<CartProductModel>)
        fun setCurrentPage(currentPage: Int)
        fun setEnableLeftButton(isEnabled: Boolean)
        fun setEnableRightButton(isEnabled: Boolean)
    }

    interface Presenter {
        fun loadCartItems()
        fun deleteCartItem(itemId: Long)
        fun decrementPage()
        fun incrementPage()
        fun changeAllCartSelectedStatus(isSelected: Boolean)
    }
}
