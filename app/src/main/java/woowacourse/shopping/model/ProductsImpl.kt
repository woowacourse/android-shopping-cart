package woowacourse.shopping.model

object ProductsImpl : ProductDao {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val products = mutableMapOf<Long, Product>()

    init {
        repeat(20) {
            save(MAC_BOOK)
        }
    }

    override fun save(product: Product): Long {
        products[id] = product.copy(id = id)
        return id++
    }

    override fun deleteAll() {
        products.clear()
    }

    override fun find(id: Long): Product {
        return products[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<Product> {
        return products.map { it.value }
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
