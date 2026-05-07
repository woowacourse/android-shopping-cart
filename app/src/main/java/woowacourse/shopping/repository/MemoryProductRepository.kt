package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import kotlin.math.min

class MemoryProductRepository(
    products: List<Product>,
) : ProductRepository {
    private val products: List<Product> = products.toList()

    override val totalSize: Int
        get() = products.size

    override fun getProduct(productId: String): Product? = products.find { it.id == productId }

    override fun getProducts(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, products.size)
        if (fromIndex >= toIndex) return emptyList()
        return products.subList(fromIndex, toIndex).toList()
    }
}
