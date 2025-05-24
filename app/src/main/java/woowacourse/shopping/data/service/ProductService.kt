package woowacourse.shopping.data.service

import woowacourse.shopping.data.model.PageableResponse
import woowacourse.shopping.data.model.ProductResponse

interface ProductService {
    fun findProductById(id: Long): ProductResponse

    fun findProductsByIds(ids: List<Long>): List<ProductResponse>

    fun loadProducts(
        offset: Int,
        limit: Int,
    ): PageableResponse<ProductResponse>
}
