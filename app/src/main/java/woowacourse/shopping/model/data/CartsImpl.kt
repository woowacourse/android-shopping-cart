package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product
import kotlin.math.min

object CartsImpl : CartDao {
    private const val OFFSET = 1
    private const val EXCEPTION_INVALID_ID = "Product not found with id: %d"
    private var id: Long = 0
    private val cart = mutableMapOf<Long, Product>()

    override fun itemSize() = cart.size

    override fun save(product: Product): Long {
        cart[id] = product.copy(id = id)
        return id++
    }

    override fun deleteAll() {
        cart.clear()
    }

    override fun delete(id: Long) {
        cart.remove(id)
    }

    override fun find(id: Long): Product {
        return cart[id] ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<Product> {
        return cart.map { it.value }
    }

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = (page - OFFSET) * pageSize
        val toIndex = min(fromIndex + pageSize, cart.size)
        return cart.values.toList().subList(fromIndex, toIndex)
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
