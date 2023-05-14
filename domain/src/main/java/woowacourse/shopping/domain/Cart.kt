package woowacourse.shopping.domain

import java.time.LocalDateTime

data class Cart(val cartProducts: List<CartProduct>) {
    fun add(cartProduct: CartProduct): Cart {
        return Cart(cartProducts + cartProduct)
    }

    fun remove(cartProduct: CartProduct): Cart {
        return Cart(cartProducts - cartProduct)
    }

    fun makeCartProduct(product: Product): CartProduct {
        return CartProduct(LocalDateTime.now(), product)
    }
}
