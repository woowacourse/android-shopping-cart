package woowacourse.shopping.domain

data class CartProducts(val value: List<CartProduct>) {
    operator fun plus(cartProducts: CartProducts): CartProducts {
        return CartProducts(value + cartProducts.value)
    }
}
