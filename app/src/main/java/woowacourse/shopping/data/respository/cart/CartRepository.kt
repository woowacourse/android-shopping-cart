package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.data.model.CartEntity

interface CartRepository {
    fun updateCartByProductId(productId: Long, count: Int)
    fun updateCartByCartId(cartId: Long, count: Int)
    fun getCarts(startPosition: Int): List<CartEntity>
    fun getAllCarts(): List<CartEntity>
    fun deleteAllCartByProductId(productId: Long)
    fun deleteCartByCartId(cartId: Long)
    fun deleteCartByProductId(productId: Long)
    fun addCart(productId: Long, count: Int)
}
