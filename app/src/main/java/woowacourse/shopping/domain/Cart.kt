package woowacourse.shopping.domain

class Cart(val cartItems: List<CartItem> = emptyList()) {
    fun plusCartItem(cartItem: CartItem): Cart {
        val isSameCartItem = cartItems.any { it.isSameCartItem(cartItem) }

        if (isSameCartItem) {
            return this
        }
        return Cart(cartItems + cartItem)
    }

    fun isContains(product: Product): Boolean = cartItems.any { it.isSame(product) }

    fun filterById(ids: List<String>): Cart = Cart(
        cartItems.filter { cartItem ->
            ids.any {
                cartItem.isSameId(it)
            }
        },
    )

    fun getProductList(): List<Product> = cartItems.map { it.product }
}
