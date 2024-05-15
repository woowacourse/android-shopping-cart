package woowacourse.shopping.data.cart

import woowacourse.shopping.model.CartItem

interface CartRepository {
    fun add(productId: Long)

    fun delete(productId: Long)

    fun deleteAll(productId: Long)

    fun findAll(): List<CartItem>
}
