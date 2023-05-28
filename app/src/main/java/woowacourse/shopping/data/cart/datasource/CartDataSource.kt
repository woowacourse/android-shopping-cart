package woowacourse.shopping.data.cart.datasource

import woowacourse.shopping.data.cart.CartProductEntity

interface CartDataSource {

    fun getCartProducts(): List<CartProductEntity>

    fun getCartProductById(id: Int): CartProductEntity

    fun getCountOfCartProducts(): Int

    fun addToCart(id: Int, count: Int)

    fun removeCartProductById(id: Int)
}
