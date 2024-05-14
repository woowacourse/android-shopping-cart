package woowacourse.shopping.model

interface CartDao {
    fun save(product: Product): Long

    fun find(id: Long): Product

    fun findAll(): List<Product>

    fun deleteAll()
}
