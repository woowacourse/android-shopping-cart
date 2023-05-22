package woowacourse.shopping.repository

import woowacourse.shopping.domain.CartProduct

interface CartRepository {
    fun findAll(): List<CartProduct>
    fun findAll(limit: Int, offset: Int): List<CartProduct>
    fun findById(productId: Long): CartProduct?
    fun save(product: CartProduct)
    fun deleteById(productId: Long)
    fun updateCount(productId: Long, count: Int)
}
