package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product

interface CartDao {
    fun save(product: Product): Long

    fun find(id: Long): Product

    fun findAll(): List<Product>

    fun deleteAll()

    fun delete(id: Long)
}
