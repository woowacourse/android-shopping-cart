package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

class MemoryProductRepository(
    products: List<Product>,
) : ProductRepository {
    private val products: List<Product> = products.toList()

    override fun getProduct(productId: Long): Product? = products.find { it.id == productId }

    override fun getProducts(): List<Product> = products.toList()
}
