package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.model.Product

class ProductDataSourceImpl(
    private val products: List<Product>,
) : ProductDataSource {
    override fun findProductById(id: Long): Product =
        products.find { it.id == id }
            ?: throw NoSuchElementException(NO_SUCH_ELEMENT_MESSAGE.format(id))

    override fun findProductsByIds(ids: List<Long>): List<Product> = products.filter { it.id in ids }

    override fun loadProducts(
        offset: Int,
        limit: Int,
    ): List<Product> {
        if (offset >= products.size) return emptyList()
        return products.drop(offset).take(limit)
    }

    override fun calculateHasMore(
        offset: Int,
        limit: Int,
    ): Boolean = offset + limit < products.size

    companion object {
        private const val NO_SUCH_ELEMENT_MESSAGE = "%d에 해당하는 상품을 찾을 수 없습니다."
    }
}
