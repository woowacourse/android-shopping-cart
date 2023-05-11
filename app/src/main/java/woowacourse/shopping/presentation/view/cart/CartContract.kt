package woowacourse.shopping.presentation.view.cart

import woowacourse.shopping.presentation.model.CartModel

interface CartContract {
    interface View {
        fun setCartItemsView(carts: List<CartModel>)
        fun updateCartItemView(carts: List<CartModel>)
        fun updateToDeleteCartItemView(position: Int)
    }

    interface Presenter {
        fun loadCartItems()
        fun deleteCartItem(position: Int)
        fun updateCartItem(currentPage: Int)
    }
}
