package woowacourse.shopping.data.cart.repository

import model.CartProduct

interface CartRepository {

    fun getCartProducts(): List<CartProduct>

    fun getCartProductById(id: Int): CartProduct

    fun getCountOfCartProducts(): Int

    fun addToCart(id: Int, count: Int = 1)

    fun removeCartProductById(id: Int)
}
