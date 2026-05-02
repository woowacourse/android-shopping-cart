package woowacourse.shopping.domain.cart.model

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
        require(isPage(page)) { "페이지는 0보다 커야 합니다." }
        val fromIndex = page * pageSize
        return cartItems.subList(fromIndex, fromIndex + pageSize)
    }

    companion object {
        fun isPage(page: Int): Boolean = page >= 0
    }
}
