package woowacourse.shopping.data.datasource.remote

import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.data.service.ProductService
import woowacourse.shopping.domain.model.Product

class ProductRemoteDataSource(
    private val service: ProductService,
) {
    fun getProductById(
        id: Long,
        onResult: (Result<Product?>) -> Unit,
    ) {
        val result = service.getProductById(id)
        onResult(Result.success(result))
    }

    fun getProductsByIds(
        ids: List<Long>,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        val result = service.getProductsByIds(ids) ?: emptyList()
        onResult(Result.success(result))
    }

    fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<PagedResult<Product>>) -> Unit,
    ) {
        val result = service.getPagedProducts(limit, offset) ?: PagedResult(emptyList(), false)
        onResult(Result.success(result))
    }
}
