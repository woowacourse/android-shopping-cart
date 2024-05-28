package woowacourse.shopping.remote.api

import woowacourse.shopping.remote.model.ProductResponse

interface ApiService {
    fun findProductById(id: Long): Result<ProductResponse>

    fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductResponse>>

    fun shutdown(): Result<Unit>
}
