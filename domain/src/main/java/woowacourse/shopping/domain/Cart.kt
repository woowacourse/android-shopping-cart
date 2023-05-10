package woowacourse.shopping.domain

data class Cart(val cartProducts: List<CartProduct>) {
    fun add(cartProduct: CartProduct): Cart {
        return Cart(cartProducts + cartProduct)
    }

    fun makeCartProduct(product: Product): CartProduct {
        val ordinal = getCurrentOrdinal()
        return CartProduct(ordinal, product)
    }

    private fun getCurrentOrdinal(): Int {
        return if (cartProducts.isEmpty()) 0 else cartProducts.last().ordinal + 1
    }

    fun remove(cartProduct: CartProduct): Cart {
        return Cart(cartProducts - cartProduct)
    }
}
