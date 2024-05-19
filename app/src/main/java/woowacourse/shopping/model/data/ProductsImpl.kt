package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product

object ProductsImpl : ProductDao {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0
    private val products = mutableMapOf<Long, Product>()

    init {
        repeat(100) {
            save(MAC_BOOK.copy(name = "맥북$it"))
            save(IPHONE.copy(name = "아이폰$it"))
            save(GALAXY_BOOK.copy(name = "갤럭시북$it"))
            save(GRAM.copy(name = "그램$it"))
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
