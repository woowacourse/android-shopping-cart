package woowacourse.shopping.domain

data class Cart(val products: List<CartOrdinalProduct>) {
    fun add(cartOrdinalProduct: CartOrdinalProduct): Cart {
        return Cart(products + cartOrdinalProduct)
    }

    fun remove(cartOrdinalProduct: CartOrdinalProduct): Cart {
        return Cart(products - cartOrdinalProduct)
    }

    fun makeCartOrdinalProduct(cartProduct: CartProduct): CartOrdinalProduct {
        val ordinal = getCurrentOrdinal()
        return CartOrdinalProduct(ordinal, cartProduct)
    }

    private fun getCurrentOrdinal(): Int {
        return (if (products.isEmpty()) 0 else products.maxOf { it.ordinal } + 1)
    }
}
