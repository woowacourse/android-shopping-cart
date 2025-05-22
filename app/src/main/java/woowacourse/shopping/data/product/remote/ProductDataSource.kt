package woowacourse.shopping.data.product.remote

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.Product

interface ProductDataSource {
    fun getAll(): List<Product>

    fun getProductById(id: Long): Product?

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product>
}
