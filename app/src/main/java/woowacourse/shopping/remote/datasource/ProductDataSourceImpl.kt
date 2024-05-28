package woowacourse.shopping.remote.datasource

import woowacourse.shopping.data.datasource.remote.ProductDataSource
import woowacourse.shopping.data.model.remote.ProductDto
import woowacourse.shopping.remote.api.ApiService
import woowacourse.shopping.remote.mapper.toData

class ProductDataSourceImpl(private val apiService: ApiService) : ProductDataSource {
    override fun findProductById(id: Long): Result<ProductDto> = apiService.findProductById(id).mapCatching { it.toData() }

    override fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<ProductDto>> =
        apiService.getPagingProduct(page, pageSize)
            .mapCatching { result -> result.map { it.toData() } }

    override fun shutdown(): Result<Unit> = apiService.shutdown()
}
