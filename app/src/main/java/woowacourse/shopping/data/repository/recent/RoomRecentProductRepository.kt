package woowacourse.shopping.data.repository.recent

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.recent.RecentProductDao
import woowacourse.shopping.data.mapper.toDomain
import woowacourse.shopping.data.mapper.toRecentProductEntity
import woowacourse.shopping.domain.product.Product
import woowacourse.shopping.domain.repository.RecentProductRepository

class RoomRecentProductRepository(
    private val recentProductDao: RecentProductDao,
    private val currentTimeMillis: () -> Long = System::currentTimeMillis,
) : RecentProductRepository {
    override fun getRecentProducts(limit: Int): Flow<List<Product>> =
        recentProductDao.getRecentProducts(limit).map { recentProducts ->
            recentProducts.map { it.toDomain() }
        }

    override suspend fun getMostRecentProduct(): Product? = recentProductDao.getMostRecentProduct()?.toDomain()

    override suspend fun save(product: Product) {
        recentProductDao.upsert(product.toRecentProductEntity(currentTimeMillis()))
        recentProductDao.deleteOlderThan(RecentProductRepository.DEFAULT_LIMIT)
    }
}
