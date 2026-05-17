package woowacourse.shopping.data.repository.recent

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.datasource.recent.RecentProductDataSource
import woowacourse.shopping.data.local.recent.RecentProductDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.mapper.toRecentProductEntity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class LocalRecentProductRepository(
    private val recentProductDataSource: RecentProductDataSource,
    private val currentTimeMillis: () -> Long = System::currentTimeMillis,
) : RecentProductRepository {
    override fun getRecentProducts(limit: Int): Flow<List<Product>> =
        recentProductDataSource.getRecentProducts(limit).map { recentProducts ->
            recentProducts.map { it.toDomain() }
        }

    override suspend fun getMostRecentProduct(product: Product): Product?{
        val recentProduct = recentProductDataSource.getMostRecentProduct()?.toDomain()
        if (recentProduct != null && !recentProduct.isSameProduct(product)) {
            return recentProduct
        }
        return null
    }

    override suspend fun save(product: Product) {
        recentProductDataSource.upsert(product.toRecentProductEntity(currentTimeMillis()))
        recentProductDataSource.deleteOlderThan(RecentProductRepository.DEFAULT_LIMIT)
    }
}
