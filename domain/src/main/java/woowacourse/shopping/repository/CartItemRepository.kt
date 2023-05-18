package woowacourse.shopping.repository

import woowacourse.shopping.domain.CartItem

interface CartItemRepository {

    fun save(cartItem: CartItem)

    fun findAll(): List<CartItem>

    fun findAllOrderByAddedTime(limit: Int, offset: Int): List<CartItem>

    fun countAll(): Int

    fun deleteByProductId(productId: Long)
}