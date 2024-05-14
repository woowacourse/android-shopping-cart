package woowacourse.shopping.model

interface ProductDao {
    fun save(product: Product): Long

    fun find(id: Long): Product

    fun findAll(
        offset: Int,
        limit: Int,
    ): List<Product>

    fun deleteAll()
}
