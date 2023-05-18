package woowacourse.shopping.database

import woowacourse.shopping.domain.CartProduct
import woowacourse.shopping.repository.CartRepository

object FakeCartRepository : CartRepository {
    private val products = mutableMapOf<Long, CartProduct>()

    override fun findAll(): List<CartProduct> {
        return products.values.toList()
    }

    override fun findAll(limit: Int, offset: Int): List<CartProduct> {
        return products.values.toList().subList(offset, offset + limit)
    }

    override fun save(product: CartProduct) {
        with(product) {
            products[this.id] = CartProduct(this.id, this.imageUrl, this.name, this.price)
        }
    }

    override fun deleteById(productId: Long) {
        products.remove(productId)
    }
}
