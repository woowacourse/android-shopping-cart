package woowacourse.shopping.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun findAll(limit: Int, offset: Int): List<Product>
    fun countAll(): Int
    fun findById(id: Long): Product?
}
