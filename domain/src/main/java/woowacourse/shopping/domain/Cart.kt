package woowacourse.shopping.domain

data class Cart(val products: List<CartProduct>) {
    fun add(cartProduct: CartProduct): Cart {
        return Cart(products + cartProduct)
    }

    fun remove(cartProduct: CartProduct): Cart {
        return Cart(products - cartProduct)
    }

    fun makeCartProduct(product: Product): CartProduct {
        val ordinal = getCurrentOrdinal()
        return CartProduct(ordinal, product)
    }

    private fun getCurrentOrdinal(): Int {
        return (if (products.isEmpty()) 0 else products.maxOf { it.ordinal } + 1)
    }
}
