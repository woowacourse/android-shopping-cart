package woowacourse.shopping.domain.cart

data class Cart(
    private val cartItems: CartItems,
) {
    val totalCount: Int = cartItems.size()

    fun addCartItem(cartItem: CartItem): Cart = copy(cartItems = cartItems.addCartItem(cartItem))

    fun removeCartItem(cartItem: CartItem): Cart = copy(cartItems = cartItems.removeCartItem(cartItem))

    fun searchCartItem(cartItem: CartItem): Boolean = cartItems.searchCartItem(cartItem)

    fun getPage(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        val fromIndex = page * pageSize
        return cartItems.subList(fromIndex, fromIndex + pageSize)
    }
}
