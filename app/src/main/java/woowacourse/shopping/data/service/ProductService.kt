package woowacourse.shopping.data.service

import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.domain.model.Product

interface ProductService {
    fun getProductById(id: Long): Product?

    fun getProductsByIds(ids: List<Long>): List<Product>?

    fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product>?
}
