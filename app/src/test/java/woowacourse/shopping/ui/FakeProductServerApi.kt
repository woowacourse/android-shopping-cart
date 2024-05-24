package woowacourse.shopping.ui

import woowacourse.shopping.data.api.ProductServerApi
import woowacourse.shopping.model.Product
import kotlin.math.min

object FakeProductServerApi : ProductServerApi {
    private var currentOffset = 0
    private const val LOAD_LIMIT = 20
    private const val EXCEPTION_INVALID_ID = "Product not found with id: %d"
    private var id: Long = 0
    private val products = mutableMapOf<Long, Product>()

    init {
        repeat(50) {
            save(Product(imageUrl = "", name = "맥북", price = 100))
        }
    }

    override fun start() {
        TODO("Not yet implemented")
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

    override fun shutdown() {
        TODO("Not yet implemented")
    }

    private fun save(product: Product): Long {
        products[id] = product.copy(id = id)
        return id++
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
