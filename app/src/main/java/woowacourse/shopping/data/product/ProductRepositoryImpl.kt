package woowacourse.shopping.data.product

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.product.remote.ProductRemoteDataSource
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository
import kotlin.concurrent.thread

class ProductRepositoryImpl(
    private val remoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    override fun getAll(): List<Product> {
        var result = emptyList<Product>()
        thread { result = remoteDataSource.getAll() }.join()
        return result
    }

    override fun getProductById(id: Long): Product? = remoteDataSource.getProductById(id)

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> {
        require(offset >= 0)
        require(limit > 0)
        var result = PagedResult<Product>(emptyList(), false)
        thread { result = remoteDataSource.getPagedProducts(limit, offset) }.join()
        return result
    }
}
