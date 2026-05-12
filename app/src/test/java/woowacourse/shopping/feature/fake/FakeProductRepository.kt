package woowacourse.shopping.feature.fake

import woowacourse.shopping.data.repository.product.ProductRepository
import woowacourse.shopping.domain.Product

class FakeProductRepository(
    private val products: List<Product>,
) : ProductRepository {

    override suspend fun loadProducts(
        startIndex: Int,
        pageSize: Int,
    ): List<Product> {
        if (startIndex >= products.size) return emptyList()
        val end = minOf(startIndex + pageSize, products.size)
        return products.subList(startIndex, end).toList()
    }

    override suspend fun getProduct(id: String): Product {
        return products.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("상품을 찾을 수 없습니다: $id")
    }
}
