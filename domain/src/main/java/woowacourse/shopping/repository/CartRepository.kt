package woowacourse.shopping.repository

import woowacourse.shopping.domain.CartProduct

interface CartRepository {
    fun findAll(): List<CartProduct>
    fun findAll(limit: Int, offset: Int): List<CartProduct>
    fun save(product: CartProduct)
    fun deleteById(productId: Long)
}
