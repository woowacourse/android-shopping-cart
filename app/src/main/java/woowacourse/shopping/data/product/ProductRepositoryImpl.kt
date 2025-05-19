package woowacourse.shopping.data.product

import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.PagedResult

class ProductRepositoryImpl(
    private val dao: ProductDao,
) : ProductRepository {
    override fun getAll(): List<Product> = dao.getAll()

    override fun getPaged(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> {
        val total = dao.count()
        if (offset >= total) return PagedResult(emptyList(), false)

        val endIndex = (offset + limit).coerceAtMost(total)
        val items = dao.getPaged(limit, offset)
        val hasNext = endIndex < total

        return PagedResult(items, hasNext)
    }
}
