package woowacourse.shopping.data.repsoitory.remote

import woowacourse.shopping.data.api.ApiService
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.remote.ProductRepository

class ProductRepositoryImpl(private val apiService: ApiService) : ProductRepository {
    override fun findProductById(id: Long): Result<Product> = apiService.findProductById(id).mapCatching { it.toDomain() }

    override fun getPagingProduct(
        page: Int,
        pageSize: Int,
    ): Result<List<Product>> =
        apiService.getPagingProduct(page, pageSize)
            .mapCatching { result -> result.map { it.toDomain() } }

    override fun shutdown(): Result<Unit> =
        runCatching {
            apiService.shutdown()
        }
}
