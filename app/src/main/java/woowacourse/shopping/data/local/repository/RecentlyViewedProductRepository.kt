package woowacourse.shopping.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import woowacourse.shopping.data.local.dao.RecentlyViewedProductDao
import woowacourse.shopping.data.local.entity.RecentlyViewedProductEntity
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.Products

class RecentlyViewedProductRepository(
    private val recentlyViewedProductDao: RecentlyViewedProductDao,
) {
    fun getAll(): Flow<List<RecentlyViewedProductEntity>?> = recentlyViewedProductDao.getAll()

    suspend fun updateList(product: Product) {
        recentlyViewedProductDao.enqueueAndLimit10(RecentlyViewedProductEntity(product.id))
    }

    fun getLatestItem(): Flow<String?> = recentlyViewedProductDao.getLatestItemId()
}
