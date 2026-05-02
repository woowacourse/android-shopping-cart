package woowacourse.shopping.domain

class Cart(val cartContents: List<CartContent> = emptyList()) {
    fun plusCartItem(cartContent: CartContent): Cart {
        val isSamerCartItem = cartContents.any { it.isSameCartItem(cartContent) }

        if (isSamerCartItem) {
            return this
        }
        return Cart(cartContents + cartContent)
    }

    fun isContains(product: Product): Boolean = cartContents.any { it.isSame(product) }

    fun filterById(ids: List<String>): Cart {
        return Cart(
            cartContents.filter { cartItem ->
                ids.any {
                    cartItem.isSameId(it)
                }
            },
        )
    }

    fun getProductList(): List<Product> {
        return cartContents.map { it.product }
    }
}
