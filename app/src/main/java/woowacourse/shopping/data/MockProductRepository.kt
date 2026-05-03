package woowacourse.shopping.data

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.model.Product

class MockProductRepository(
    private val source: List<Product> = ProductData.products,
) : ProductRepository {
    override suspend fun getProductById(id: String): Product =
        requireNotNull(source.firstOrNull { it.id == id }) {
            "존재하지 않는 상품입니다."
        }

    override suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ImmutableList<Product> {
        require(offset >= 0) { "offset은 0 이상이어야 합니다." }

        if (offset >= source.size) return persistentListOf()
        val toIndex = minOf(offset + limit, source.size)
        return source.subList(offset, toIndex).toImmutableList()
    }
}
