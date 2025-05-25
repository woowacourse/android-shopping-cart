package woowacourse.shopping.data.datasource

import woowacourse.shopping.domain.model.Product

interface ProductDataSource {
    fun start(): Result<Unit>

    fun fetchPagingProducts(
        page: Int,
        pageSize: Int,
    ): Result<List<Product>>

    fun fetchProductById(id: Long): Result<Product>

    fun shutdown(): Result<Unit>
}
