package woowacourse.shopping.repository.room

import woowacourse.shopping.local.recent.RecentProductDao
import woowacourse.shopping.local.recent.RecentProductEntity
import woowacourse.shopping.model.ProductId
import woowacourse.shopping.model.RecentProduct
import woowacourse.shopping.repository.RecentProductRepository

class RoomRecentProductRepository(
    private val recentProductDao: RecentProductDao,
) : RecentProductRepository {
    companion object {
        private const val MAX_RECENT_PRODUCTS = 10
    }

    override suspend fun recordView(productId: ProductId) {
        recentProductDao.upsert(
            RecentProductEntity.fromDomain(
                RecentProduct(
                    productId = productId,
                    viewedAtMillis = System.currentTimeMillis(),
                ),
            ),
        )
        recentProductDao.trimTo(MAX_RECENT_PRODUCTS)
    }

    override suspend fun getRecentProducts(limit: Int): List<RecentProduct> =
        recentProductDao
            .getRecentProducts(limit.coerceAtLeast(0))
            .map(RecentProductEntity::toDomain)

    override suspend fun getLatestViewedProductExcluding(productId: ProductId): RecentProduct? =
        recentProductDao
            .getLatestViewedProductExcluding(productId.value.toString())
            ?.toDomain()
}
