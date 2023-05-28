package woowacourse.shopping.data.cart.cache

import woowacourse.shopping.data.cart.CartProductEntity

interface CartCache {

    fun getCartProducts(): List<CartProductEntity>

    fun getCartProductById(id: Int): CartProductEntity

    fun getCountOfCartProducts(): Int

    fun addToCart(id: Int, count: Int)

    fun removeCartProductById(id: Int)
}
