package woowacourse.shopping.data.product

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.Product

interface ProductRepository {
    fun getAll(): List<Product>

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product>
}
