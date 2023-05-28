package woowacourse.shopping.data.respository.cart

import woowacourse.shopping.data.model.CartEntity

interface CartRepository {
    fun updateCartByProductId(productId: Long, count: Int, checked: Int)
    fun updateCartCountByCartId(cartId: Long, count: Int)
    fun updateCartCheckedByCartId(cartId: Long, checked: Boolean)
    fun getCarts(startPosition: Int): List<CartEntity>
    fun getAllCarts(): List<CartEntity>
    fun deleteAllCartByProductId(productId: Long)
    fun deleteCartByCartId(cartId: Long)
    fun deleteCartByProductId(productId: Long)
    fun addCart(productId: Long, count: Int)
}
