package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun getAll(): List<Product>

    fun getProductById(id: Long): Product?

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product>
}
