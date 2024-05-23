package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product
import kotlin.math.min

object ProductsImpl : ProductDao {
    private var currentOffset = 0
    private const val LOAD_LIMIT = 20
    private const val EXCEPTION_INVALID_ID = "Product not found with id: %d"
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

    override fun findAll(): List<Product> {
        return products.map { it.value }
    }

    override fun getProducts(): List<Product> {
        val fromIndex = currentOffset
        currentOffset = min(currentOffset + LOAD_LIMIT, products.size)
        return products.values.toList().subList(
            fromIndex,
            currentOffset,
        )
    }

    override fun getLastProducts(): List<Product> {
        return products.values.toList().subList(0, currentOffset)
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
