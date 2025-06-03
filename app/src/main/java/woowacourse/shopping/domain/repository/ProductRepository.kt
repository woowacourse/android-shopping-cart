package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun getProductById(
        id: Long,
        onResult: (Result<Product?>) -> Unit,
    )

    fun getProductsByIds(
        ids: List<Long>,
        onResult: (Result<List<Product>>) -> Unit,
    )

    fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<PagedResult<Product>>) -> Unit,
    )
}
