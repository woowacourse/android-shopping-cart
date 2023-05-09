package woowacourse.shopping.repository

import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun findAll(): List<Product>
    fun findAll(rowNum: Int, limit: Int): List<Product>
    fun findById(id: Long): Product?
}
