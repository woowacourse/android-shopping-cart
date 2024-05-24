package woowacourse.shopping.data.api

import woowacourse.shopping.data.model.remote.ProductResponse

interface ApiService {
    fun findProductById(id: Long): Result<ProductResponse>

    fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductResponse>>

    fun shutdown(): Result<Unit>
}
