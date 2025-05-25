package woowacourse.shopping.domain.repository

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.domain.model.Product

interface ProductRepository {
    fun getProductById(
        id: Long,
        onSuccess: (Product?) -> Unit,
    )

    fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (PagedResult<Product>) -> Unit,
    )
}
