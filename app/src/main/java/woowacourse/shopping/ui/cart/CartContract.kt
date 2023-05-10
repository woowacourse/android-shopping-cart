package woowacourse.shopping.ui.cart

interface CartContract {
    interface Presenter {
        fun loadCartItems()
        fun deleteCartItem(productId: Long)
    }

    interface View {
        fun setCartItems(cartItems: List<CartUIState>)
    }
}
