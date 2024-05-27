package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.Product
import kotlin.math.min

class FakeProductRepository(
    private val products: List<Product>,
) : ProductRepository {
    override fun fetchCurrentPage(): List<Product> {
        val fromIndex = 1 * 20
        val toIndex = min(fromIndex + 20, products.size)

        if (fromIndex > toIndex) return emptyList()

        return products.subList(fromIndex, toIndex)
    }

    override fun fetchNextPage(): List<Product> {
        val fromIndex = 0 * 20
        val toIndex = min(fromIndex + 20, products.size)

        if (fromIndex > toIndex) return emptyList()

        return products.subList(fromIndex, toIndex)
    }

    override fun fetchProduct(id: Long): Product {
        return products.first { it.id == id }
    }

    override fun fetchProducts(ids: List<Long>): List<Product> {
        return products
    }
}
