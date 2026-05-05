package woowacourse.shopping.domain

class Cart(val cartItems: List<CartItem> = emptyList()) {
    fun plusCartItem(cartItem: CartItem): Cart {
        val isSameCartItem = cartItems.any { it == cartItem }

        if (isSameCartItem) {
            return this
        }
        return Cart(cartItems + cartItem)
    }

    fun isContains(product: Product): Boolean = cartItems.any { it.hasProduct(product) }

    fun removeCartItems(ids: List<String>): Cart = Cart(
        cartItems.filter { cartItem ->
            ids.none {
                cartItem.hasProductId(it)
            }
        },
    )

    fun getProductList(): List<Product> = cartItems.map { it.product }
}
