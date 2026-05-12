package woowacourse.shopping.data.repository

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import woowacourse.shopping.data.source.remote.ProductRemoteDataSource
import woowacourse.shopping.data.source.remote.dto.toDomain
import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.repository.ProductRepository

class RemoteProductRepository(
    private val remoteDataSource: ProductRemoteDataSource = ProductRemoteDataSource(),
) : ProductRepository {
    override suspend fun getProducts(
        offset: Int,
        limit: Int,
    ): ImmutableList<Product> =
        remoteDataSource
            .fetchProducts(offset, limit)
            .map { it.toDomain() }
            .toImmutableList()

    override suspend fun getProductById(id: Long): Product = remoteDataSource.fetchProductById(id).toDomain()

    override suspend fun getProductsByIds(ids: List<Long>): List<Product> = remoteDataSource.fetchProductsByIds(ids).map { it.toDomain() }
}
