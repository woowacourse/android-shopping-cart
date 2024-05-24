package woowacourse.shopping.data.product

import woowacourse.shopping.model.Product

interface ProductDao {
    fun find(id: Long): Product

    fun getProducts(): List<Product>

    fun insert(product: Product): Long

    fun findAll(): List<Product>

    fun deleteAll()
}
