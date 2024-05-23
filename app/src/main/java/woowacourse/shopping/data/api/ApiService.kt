package woowacourse.shopping.data.api

import woowacourse.shopping.data.model.remote.ProductEntity

interface ApiService {
    fun findProductById(id: Long): Result<ProductEntity>

    fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductEntity>>

    fun shutdown(): Result<Unit>
}
