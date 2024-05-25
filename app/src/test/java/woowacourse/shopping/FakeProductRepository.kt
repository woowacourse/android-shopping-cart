package woowacourse.shopping

import woowacourse.shopping.data.product.ProductRepository
import woowacourse.shopping.data.product.entity.Product
import kotlin.math.min

class FakeProductRepository(savedProducts: List<Product> = emptyList()) : ProductRepository {
    private val products: MutableMap<Long, Product> = savedProducts.associateBy { it.id }.toMutableMap()

    override fun find(id: Long): Product {
        return products[id] ?: throw IllegalArgumentException()
    }

    override fun findRange(
        page: Int,
        pageSize: Int,
    ): List<Product> {
        val fromIndex = page * pageSize
        val toIndex = min(fromIndex + pageSize, products.size)
        return products.map { it.value }.subList(fromIndex, toIndex)
    }

    override fun totalProductCount(): Int = products.size
}
