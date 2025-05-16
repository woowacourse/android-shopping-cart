package woowacourse.shopping.data

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val products: List<Product>,
) : ProductRepository {
    override fun findProductById(
        id: Long,
        callback: (Product?) -> Unit,
    ) {
        val product = products.find { it.id == id }
        callback(product)
    }

    override fun findProductsByIds(
        ids: List<Long>,
        callback: (List<Product>) -> Unit,
    ) {
        val products = products.filter { ids.contains(it.id) }
        callback(products)
    }

    override fun loadProducts(
        lastItemId: Long,
        loadSize: Int,
        callback: (List<Product>, Boolean) -> Unit,
    ) {
        val products = products.filter { it.id > lastItemId }.take(loadSize)
        val lastId = products.lastOrNull()?.id ?: return callback(products, false)

        val hasMore = this.products.any { it.id > lastId }

        callback(products, hasMore)
    }
}
