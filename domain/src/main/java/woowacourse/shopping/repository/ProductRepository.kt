package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface ProductRepository {
    fun getAll(): List<Product>
    fun getNext(count: Int): List<Product>
    fun insert(product: Product): Int
    fun findById(id: Int): Product
}
