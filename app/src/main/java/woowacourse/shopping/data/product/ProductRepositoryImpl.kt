package woowacourse.shopping.data.product

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.product.remote.ProductRemoteDataSource
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val remoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    override fun getProductById(
        id: Long,
        onSuccess: (Product?) -> Unit,
    ) {
        thread {
            onSuccess(remoteDataSource.getProductById(id))
        }
    }

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
        onSuccess: (PagedResult<Product>) -> Unit,
    ) {
        require(offset >= 0)
        require(limit > 0)
        thread {
            onSuccess(remoteDataSource.getPagedProducts(limit, offset))
        }
    }
}
