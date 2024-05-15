package woowacourse.shopping.model

import kotlin.math.min

object ProductsImpl : ProductDao {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private const val LOAD_LIMIT = 20
    private var currentOffset = 0
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
    }

    override fun find(id: Long): Product {
        return products[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findInRange(): List<Product> {
        val endRange = min(currentOffset + LOAD_LIMIT, products.size)
        val productsInRange = products.values.toList().subList(currentOffset, endRange)
        currentOffset = endRange

        return productsInRange
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
