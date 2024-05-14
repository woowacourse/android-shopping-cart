package woowacourse.shopping

interface ProductRepository {
    fun find(id: Long): Product

    fun findAll(): List<Product>

    fun save(
        imageUrl: String,
        title: String,
        price: Int,
    ): Long
}
