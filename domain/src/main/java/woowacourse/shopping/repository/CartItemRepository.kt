package woowacourse.shopping.repository

import woowacourse.shopping.domain.CartItem

interface CartItemRepository {

    fun save(cartItem: CartItem)

    fun findAllByIds(ids: List<Long>): List<CartItem>

    fun findAllOrderByAddedTime(limit: Int, offset: Int): List<CartItem>

    fun findById(id: Long): CartItem?

    fun countAll(): Int

    fun updateCountById(id: Long, count: Int)

    fun deleteById(id: Long)
}