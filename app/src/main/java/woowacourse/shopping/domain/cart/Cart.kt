package woowacourse.shopping.domain.cart

data class Cart(
    val cartItems: CartItems,
) {
    val totalCount: Int = cartItems.size()

    fun addCartItem(cartItem: CartItem): Cart = copy(cartItems = cartItems.addCartItem(cartItem))

    fun removeCartItem(cartItem: CartItem): Cart = copy(cartItems = cartItems.removeCartItem(cartItem))

    fun getPage(
        page: Int,
        pageSize: Int,
    ): List<CartItem> {
        require(page >= 0) { "page는 0 이상이어야 합니다. page=$page" }
        require(pageSize > 0) { "pageSize는 1 이상이어야 합니다. pageSize=$pageSize" }

        val fromIndex = page * pageSize
        val toIndex = fromIndex + pageSize
        return cartItems.subList(fromIndex, toIndex)
    }
}
