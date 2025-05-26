package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.CartItem

interface CartItemRepository {
    fun findCartItemById(id: Long): CartItem?
    fun getCartItems(): List<CartItem>
    fun getPagedCartItems(limit: Int, offset: Int): List<CartItem>
    fun insert(cartItem: CartItem)
    fun delete(cartItem: CartItem)
    fun deleteById(id: Long)
    fun update(newCartItem: CartItem)
    fun increaseQuantity(id: Long)
    fun decreaseQuantity(id: Long)
}
