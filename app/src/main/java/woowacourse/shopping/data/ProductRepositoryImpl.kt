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
        val filteredProducts = products.filter { it.id in ids }
        callback(filteredProducts)
    }

    override fun loadProducts(
        offset: Int,
        loadSize: Int,
        callback: (List<Product>, Boolean) -> Unit,
    ) {
        val totalSize = products.size

        if (offset >= totalSize) {
            callback(emptyList(), false)
            return
        }

        val sublist = products.drop(offset).take(loadSize)
        val hasMore = offset + loadSize < totalSize

        callback(sublist, hasMore)
    }
}
