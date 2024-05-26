package woowacourse.shopping.data.datasource.impl

import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.datasource.RecentProductDataSource
import woowacourse.shopping.data.entity.RecentProductEntity
import woowacourse.shopping.data.entity.RecentProductEntity.Companion.STUB
import kotlin.concurrent.thread

class RoomRecentProductDataSource(private val recentDao: RecentProductDao) : RecentProductDataSource {
    override fun insert(recentProduct: RecentProductEntity) {
        thread {
            recentDao.insert(recentProduct)
        }.join()
    }

    override fun lastRecentProduct(): RecentProductEntity? {
        var recent: RecentProductEntity? = STUB
        thread {
            recent = recentDao.lastRecentProduct()
        }.join()
        return recent
    }

    override fun recentByProductId(productId: Long): RecentProductEntity {
        var recent = STUB
        thread {
            recent = recentDao.recentByProductId(productId)
        }.join()
        return recent
    }

    override fun recentProducts(): List<RecentProductEntity> {
        var recentProducts = emptyList<RecentProductEntity>()
        thread {
            recentProducts = recentDao.recentProducts()
        }.join()
        return recentProducts
    }
}
