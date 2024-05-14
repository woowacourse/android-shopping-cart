package woowacourse.shopping.domain

interface CartDataSource {
    fun getCartItems(
        page: Int,
        pageSize: Int,
    ): List<CartItem>

    fun addCartItem(cartItem: CartItem): Long
}
