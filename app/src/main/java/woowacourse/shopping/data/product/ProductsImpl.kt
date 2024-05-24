package woowacourse.shopping.data.product

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
            insert(MAC_BOOK.copy(name = "맥북$it"))
            insert(IPHONE.copy(name = "아이폰$it"))
            insert(GALAXY_BOOK.copy(name = "갤럭시북$it"))
            insert(GRAM.copy(name = "그램$it"))
        }
    }

    override fun find(id: Long): Product {
        return products[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun getProducts(): List<Product> {
        val fromIndex = currentOffset
        currentOffset = min(currentOffset + LOAD_LIMIT, products.size)
        return products.values.toList().subList(
            fromIndex,
            currentOffset,
        )
    }

    override fun findAll(): List<Product> {
        return products.map { it.value }
    }

    override fun insert(product: Product): Long {
        products[id] = product.copy(id = id)
        return id++
    }

    override fun deleteAll() {
        products.clear()
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
