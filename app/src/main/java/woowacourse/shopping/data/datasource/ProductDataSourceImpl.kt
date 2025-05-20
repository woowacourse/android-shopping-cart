package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.model.PageableItem
import woowacourse.shopping.domain.model.Product

class ProductDataSourceImpl(
    private val products: List<Product>,
) : ProductDataSource {
    override fun loadProducts(
        offset: Int,
        loadSize: Int,
    ): Result<PageableItem<Product>> =
        runCatching {
            val totalSize = products.size

            if (offset >= totalSize) {
                return@runCatching PageableItem(emptyList(), false)
            }

            val sublist = products.drop(offset).take(loadSize)
            val hasMore = offset + loadSize < totalSize

            PageableItem(sublist, hasMore)
        }

    override fun findProductById(id: Long): Result<Product> =
        runCatching {
            products.find { it.id == id }
                ?: throw NoSuchElementException(NO_SUCH_ELEMENT_MESSAGE.format(id))
        }

    override fun findProductsByIds(ids: List<Long>): Result<List<Product>> = runCatching { products.filter { it.id in ids } }

    companion object {
        private const val NO_SUCH_ELEMENT_MESSAGE = "%d에 해당하는 상품을 찾을 수 없습니다."
    }
}
