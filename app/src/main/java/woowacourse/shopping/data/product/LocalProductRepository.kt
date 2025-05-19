package woowacourse.shopping.data.product

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.domain.Product

class LocalProductRepository : ProductRepository {
    override fun getAll(): List<Product> = ProductData.products

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> {
        require(offset >= 0)
        require(limit > 0)

        val total = getAll().size
        if (offset >= total) return PagedResult(emptyList(), false)

        val endIndex = (offset + limit).coerceAtMost(total)
        val items = getAll().subList(offset, endIndex)
        val hasNext = endIndex < total

        return PagedResult(items, hasNext)
    }
}
