package woowacourse.shopping.domain

data class Cart(val cartProducts: List<CartProduct>) {
    fun add(cartProduct: CartProduct): Cart {
        return Cart(cartProducts + cartProduct)
    }

    fun remove(cartProduct: CartProduct): Cart {
        return Cart(cartProducts - cartProduct)
    }

    fun getSubCart(from: Int, to: Int): Cart {
        return Cart(cartProducts.subList(from, to))
    }
}
