package woowacourse.shopping.domain

class Cart(val cartItems: List<CartItem> = emptyList()) {
    fun plusCartItem(cartItem: CartItem): Cart {
        val isSamerCartItem = cartItems.any { it.isSameCartItem(cartItem) }

        if (isSamerCartItem) {
            return this
        }
        return Cart(cartItems + cartItem)
    }

    fun isContains(product: Product): Boolean = cartItems.any { it.isSame(product) }

    fun filterById(ids: List<String>): Cart {
        return Cart(
            cartItems.filter { cartItem ->
                ids.any {
                    cartItem.isSameId(it)
                }
            },
        )
    }

    fun getProductList(): List<Product> {
        return cartItems.map { it.product }
    }
}
