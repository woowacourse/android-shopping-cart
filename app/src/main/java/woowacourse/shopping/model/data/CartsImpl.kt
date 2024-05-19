package woowacourse.shopping.model.data

import woowacourse.shopping.model.Product

object CartsImpl : CartDao {
    private const val EXCEPTION_INVALID_ID = "Movie not found with id: %d"
    private var id: Long = 0

    //    private val cart = mutableMapOf<Long, Product>()
    private val cart = mutableListOf<Product>()

    override fun save(product: Product): Long {
        cart.add(product.copy(id = id))
        return id++
    }

    override fun deleteAll() {
        cart.clear()
        id = 0
    }

    override fun delete(id: Long) {
        cart.remove(
            cart.find { it.id == id } ?: throw NoSuchElementException(invalidIdMessage(id)),
        )
    }

    override fun find(id: Long): Product {
        return cart.find { it.id == id } ?: throw NoSuchElementException(invalidIdMessage(id))
    }

    override fun findAll(): List<Product> {
        return cart
    }

    private fun invalidIdMessage(id: Long) = EXCEPTION_INVALID_ID.format(id)
}
