package woowacourse.shopping.data.cart

import woowacourse.shopping.model.Quantity

interface CartRepository {
    fun add(productId: Long)

    fun delete(productId: Long)

    fun findAll(): Map<Long, Quantity>
}
