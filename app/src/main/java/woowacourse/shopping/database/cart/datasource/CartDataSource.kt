package woowacourse.shopping.database.cart.datasource

import woowacourse.shopping.database.cart.CartProductEntity

interface CartDataSource {

    fun getCartProducts(): List<CartProductEntity>

    fun getCartProductById(id: Int): CartProductEntity

    fun getCountOfCartProducts(): Int

    fun addToCart(id: Int, count: Int)

    fun removeCartProductById(id: Int)
}
