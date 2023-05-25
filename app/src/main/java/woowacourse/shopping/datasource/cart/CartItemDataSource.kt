package woowacourse.shopping.datasource.cart

import woowacourse.shopping.domain.CartItem

interface CartItemDataSource {

    fun save(cartItem: CartItem)

    fun findAllByIds(ids: List<Long>): List<CartItem>

    fun findAllOrderByAddedTime(limit: Int, offset: Int): List<CartItem>

    fun findById(id: Long): CartItem?

    fun findByProductId(productId: Long): CartItem?

    fun countAll(): Int

    fun existByProductId(productId: Long): Boolean

    fun updateCountById(id: Long, count: Int)

    fun deleteById(id: Long)
}