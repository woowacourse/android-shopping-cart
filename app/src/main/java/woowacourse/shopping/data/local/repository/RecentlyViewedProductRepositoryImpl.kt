package woowacourse.shopping.data.local.repository

import kotlinx.coroutines.flow.Flow
import woowacourse.shopping.data.local.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity
import woowacourse.shopping.domain.Product

class RecentlyViewedProductRepositoryImpl(
    private val recentlyViewedProductDao: RecentlyViewedProductDao,
) : RecentlyViewedProductRepository {
    override fun getAll(): Flow<List<RecentlyViewedProductEntity>?> = recentlyViewedProductDao.getAll()

    override suspend fun updateList(product: Product) {
        recentlyViewedProductDao.enqueueAndLimit10(RecentlyViewedProductEntity(product.id))
    }

    override fun getLatestItem(): Flow<String?> = recentlyViewedProductDao.getLatestItemId()
}
