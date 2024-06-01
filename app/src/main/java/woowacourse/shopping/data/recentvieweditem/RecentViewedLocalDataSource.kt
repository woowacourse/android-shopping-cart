package woowacourse.shopping.data.recentvieweditem

import java.time.LocalDateTime
import kotlin.concurrent.thread

class RecentViewedLocalDataSource(recentViewedItemDatabase: RecentViewedItemDatabase) {
    private val recentViewedItemDao = recentViewedItemDatabase.recentViewedItemDao()

    fun loadAll(): List<RecentViewedItemEntity> {
        val loaded = mutableListOf<RecentViewedItemEntity>()
        thread { loaded.addAll(recentViewedItemDao.findAllItemsByMostRecent()) }.join()
        return loaded
    }

    fun findByProductId(productId: Long): RecentViewedItemEntity? {
        var item: RecentViewedItemEntity? = null
        thread { item = recentViewedItemDao.findItemByProductId(productId) }.join()
        return item
    }

    fun save(newEntity: RecentViewedItemEntity) {
        thread { recentViewedItemDao.saveRecentViewedItem(newEntity) }.join()
    }

    fun update(
        productId: Long,
        updatedTime: LocalDateTime,
    ) {
        thread { recentViewedItemDao.updateViewedDate(productId, updatedTime) }.join()
    }

    fun findMostRecent(): RecentViewedItemEntity? {
        var item: RecentViewedItemEntity? = null
        thread { item = recentViewedItemDao.findItemByMostRecent() }.join()
        return item
    }
}
