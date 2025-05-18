package woowacourse.shopping.data

import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val products: List<Product>,
) : ProductRepository {
    override fun findProductById(
        id: Long,
        onResult: (Result<Product>) -> Unit,
    ) {
        val result =
            runCatching {
                products.find { it.id == id }
                    ?: throw NoSuchElementException("${id}에 해당하는 상품을 찾을 수 없습니다.")
            }
        onResult(result)
    }

    override fun findProductsByIds(
        ids: List<Long>,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        val result = runCatching { products.filter { it.id in ids } }
        onResult(result)
    }

    override fun loadProducts(
        offset: Int,
        loadSize: Int,
        onResult: (Result<PageableItem<Product>>) -> Unit,
    ) {
        val result =
            runCatching {
                val totalSize = products.size

                if (offset >= totalSize) {
                    return@runCatching PageableItem(emptyList(), false)
                }

                val sublist = products.drop(offset).take(loadSize)
                val hasMore = offset + loadSize < totalSize

                PageableItem(sublist, hasMore)
            }

        onResult(result)
    }
}
