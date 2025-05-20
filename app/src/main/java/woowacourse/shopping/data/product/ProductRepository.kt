package woowacourse.shopping.data.product

import woowacourse.shopping.domain.Product
import woowacourse.shopping.view.PagedResult

interface ProductRepository {
    fun getAll(): List<Product>

    fun getPaged(
        limit: Int,
        offset: Int,
    ): PagedResult<Product>
}
