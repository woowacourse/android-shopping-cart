package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product

object ProductsImpl : ProductDao {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val products = mutableMapOf<Long, Product>()

    init {
        repeat(100) {
            save(MAC_BOOK)
            save(IPHONE)
            save(GALAXY_BOOK)
            save(GRAM)
        }
    }

    override fun save(product: Product): Long {
        products[id] = product.copy(id = id)
        return id++
    }

    override fun deleteAll() {
        products.clear()
        id = 0
    }

    override fun find(id: Long): Product {
        return products[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<Product> {
        return products.map { it.value }
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
