package woowacourse.shopping.domain.cart.model

data class Cart(
    private val cartItems: CartItems,
) {
    fun getCartSize(): Int = cartItems.size()

    fun addCartItem(
        cartItem: CartItem,
        targetQuantity: Int,
    ): Cart = copy(cartItems = cartItems.addCartItem(cartItem, targetQuantity))

    fun minusCartItem(
        cartItem: CartItem,
        targetQuantity: Int,
    ): Cart = copy(cartItems = cartItems.minusCartItem(cartItem, targetQuantity))

    fun removeCartItem(cartItem: CartItem): Cart = copy(cartItems = cartItems.removeCartItem(cartItem))

    fun searchCartItem(cartItem: CartItem): Boolean = cartItems.searchCartItem(cartItem)

    fun getTotalCartItemCount(): Int = cartItems.getTotalCartItemCount()

    fun getQuantity(cartItem: CartItem): Int = cartItems.getQuantity(cartItem)

    fun getPage(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        require(isPageValid(page)) { "페이지는 0보다 작을 수 없다." }
        val fromIndex = page * pageSize
        return cartItems.subList(fromIndex, fromIndex + pageSize)
    }

    companion object {
        fun isPageValid(page: Int): Boolean = page >= 0
    }
}
