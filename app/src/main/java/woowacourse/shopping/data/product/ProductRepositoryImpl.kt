package woowacourse.shopping.data.product

import woowacourse.shopping.data.dummy.ProductData
import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.PagedResult

class ProductRepositoryImpl : ProductRepository {
    override fun getAll(): List<Product> = ProductData.products

    override fun getPaged(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> {
        val total = getAll().size
        if (offset >= total) return PagedResult(emptyList(), false)

        val endIndex = (offset + limit).coerceAtMost(total)
        val items = ProductData.products.subList(offset, endIndex)
        val hasNext = endIndex < total

        return PagedResult(items, hasNext)
    }
}
