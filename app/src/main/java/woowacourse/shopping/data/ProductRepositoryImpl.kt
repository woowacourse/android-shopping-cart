package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val products: List<Product>,
) : ProductRepository {
    override fun loadProducts(
        lastItemId: Long,
        loadSize: Int,
    ): Pair<List<Product>, Boolean> {
        val products = products.filter { it.id > lastItemId }.take(loadSize)
        val lastId = products.lastOrNull()?.id ?: return products to false
        val hasMore = this.products.any { it.id > lastId }

        return products to hasMore
    }
}
