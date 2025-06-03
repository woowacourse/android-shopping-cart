package woowacourse.shopping.data.repository

import woowacourse.shopping.data.datasource.remote.ProductRemoteDataSource
import woowacourse.shopping.data.model.PagedResult
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val remoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    override fun getProductById(
        id: Long,
        onResult: (Result<Product?>) -> Unit,
    ) {
        remoteDataSource.getProductById(id, onResult)
    }

    override fun getProductsByIds(
        ids: List<Long>,
        onResult: (Result<List<Product>>) -> Unit,
    ) {
        remoteDataSource.getProductsByIds(ids, onResult)
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onResult: (Result<PagedResult<Product>>) -> Unit,
    ) {
        require(offset >= 0)
        require(limit > 0)
        thread {
            remoteDataSource.getPagedProducts(limit, offset, onResult)
        }
    }
}
