package woowacourse.shopping.repository

import woowacourse.shopping.domain.Product

interface CartRepository {
    fun findAll(): List<Product>
    fun findAll(limit: Int, offset: Int): List<Product>
    fun save(product: Product)
    fun deleteById(productId: Long)
}
