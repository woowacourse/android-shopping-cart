package woowacourse.shopping.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun findAll(): List<Product>
    fun findAll(limit: Int, offset: Int): List<Product>
    fun findById(id: Long): Product?
}
