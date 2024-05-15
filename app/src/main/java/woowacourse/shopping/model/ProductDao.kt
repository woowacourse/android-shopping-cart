package woowacourse.shopping.model

interface ProductDao {
    fun save(product: Product): Long

    fun find(id: Long): Product

    fun findInRange(): List<Product>

    fun deleteAll()
}
