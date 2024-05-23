package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product

interface ProductDao {
    fun save(product: Product): Long

    fun find(id: Long): Product

    fun findAll(): List<Product>

    fun deleteAll()

    fun getProducts(): List<Product>

    fun getLastProducts(): List<Product>
}
