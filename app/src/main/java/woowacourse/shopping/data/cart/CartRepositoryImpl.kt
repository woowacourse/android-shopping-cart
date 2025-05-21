package woowacourse.shopping.data.cart

import woowacourse.shopping.model.product.Product

object CartRepositoryImpl : CartRepository {
    private val products = mutableListOf<Product>()

    override fun getAll(): List<Product> = products.toList()

    override fun add(product: Product) {
        products.add(product)
    }

    override fun remove(product: Product) {
        products.remove(product)
    }

    override fun clear() {
        products.clear()
    }
}
