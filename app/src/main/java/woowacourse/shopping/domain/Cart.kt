package woowacourse.shopping.domain

import java.util.UUID

class Cart(val cartProducts: CartProducts) {
    fun addProduct(product: Product): Cart {
        val product = cartProducts.add(product)
        return Cart(product)
    }

    fun removeProduct(id: UUID): Cart {
        val product = cartProducts.remove(id)
        return Cart(product)
    }
}
