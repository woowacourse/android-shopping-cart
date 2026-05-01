package woowacourse.shopping.data

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class InMemoryProductRepository(
    private val source: List<Product> = ProductData.products,
) : ProductRepository {
    override suspend fun getProductById(id: String): Product = source.first { it.id == id }

    override suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ImmutableList<Product> {
        if (offset >= source.size) return persistentListOf()
        val toIndex = minOf(offset + limit, source.size)
        return source.subList(offset, toIndex).toImmutableList()
    }
}
