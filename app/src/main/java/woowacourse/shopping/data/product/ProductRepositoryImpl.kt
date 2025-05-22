package woowacourse.shopping.data.product

import woowacourse.shopping.data.PagedResult
import woowacourse.shopping.data.product.remote.ProductRemoteDataSource
import woowacourse.shopping.domain.Product

class ProductRepositoryImpl(
    private val remoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    override fun getAll(): List<Product> = remoteDataSource.getAll()

    override fun getProductById(id: Long): Product? = remoteDataSource.getProductById(id)

    override fun getPagedProducts(
        limit: Int,
        offset: Int,
    ): PagedResult<Product> {
        require(offset >= 0)
        require(limit > 0)
        return remoteDataSource.getPagedProducts(limit, offset)
    }
}
