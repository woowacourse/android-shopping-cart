package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.Products

class FakeProductRepository(
    products: List<Product>,
) : ProductRepository {
    private val products = Products(products)
    private val productMap: Map<ProductId, Product> = products.associateBy { it.id }

    override val size: Int
        get() = products.count()

    override suspend fun getProducts(
        fromIndex: Int,
        limit: Int,
    ): Products = products.getPagedProducts(fromIndex, limit)

    override suspend fun hasNext(current: Int): Boolean = current < products.toList().lastIndex

    override suspend fun findAllByIds(ids: Set<ProductId>): Map<ProductId, Product> =
        ids
            .mapNotNull { id ->
                productMap[id]?.let { id to it }
            }.toMap()
}
