package woowacourse.shopping.database

import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.CartRepository

object FakeCartRepository : CartRepository {
    private val products = mutableMapOf<Long, Product>()

    override fun findAll(): List<Product> {
        return products.values.toList()
    }

    override fun findAll(limit: Int, offset: Int): List<Product> {
        return products.values.toList().subList(offset, offset + limit)
    }

    override fun save(product: Product) {
        products[product.id] = product
    }

    override fun deleteById(productId: Long) {
        products.remove(productId)
    }
}
