package woowacourse.shopping.domain

data class Cart(val products: List<CartProduct>) {
    fun add(cartProduct: CartProduct): Cart {
        return Cart(products + cartProduct)
    }

    fun remove(cartProduct: CartProduct): Cart {
        return Cart(products - cartProduct)
    }
}
