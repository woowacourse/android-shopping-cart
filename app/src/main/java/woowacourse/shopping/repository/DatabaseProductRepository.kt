package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

class DatabaseProductRepository(
    private val remoteDataSource: ProductRemoteDataSource,
) : ProductRepository {
    override suspend fun totalSize(): Int = remoteDataSource.getTotalSize()

    override suspend fun getProduct(productId: String): Product? = remoteDataSource.getProduct(productId)

    override suspend fun getProducts(
        offset: Int,
        size: Int,
    ): List<Product> = remoteDataSource.getProducts(offset, size)
}
