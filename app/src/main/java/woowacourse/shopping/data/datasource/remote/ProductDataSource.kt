package woowacourse.shopping.data.datasource.remote

import woowacourse.shopping.data.model.remote.ProductDto

interface ProductDataSource {
    fun findProductById(id: Long): Result<ProductDto>

    fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductDto>>

    fun shutdown(): Result<Unit>
}
