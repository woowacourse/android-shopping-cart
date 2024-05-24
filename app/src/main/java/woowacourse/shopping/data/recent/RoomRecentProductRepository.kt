package woowacourse.shopping.data.recent

import woowacourse.shopping.data.recent.dao.RecentProductDao
import woowacourse.shopping.data.recent.entity.RecentProduct
import java.time.LocalDateTime
import kotlin.concurrent.thread

class RoomRecentProductRepository(private val recentProductDao: RecentProductDao) : RecentProductRepository {
    override fun findLastOrNull(): RecentProduct? {
        var lastRecentProduct: RecentProduct? = null
        thread {
            lastRecentProduct = recentProductDao.findRange(1).firstOrNull()
        }.join()
        return lastRecentProduct
    }

    override fun findRecentProducts(): List<RecentProduct> {
        var lastRecentProduct: List<RecentProduct> = emptyList()
        thread {
            lastRecentProduct = recentProductDao.findRange(FIND_RECENT_PRODUCTS_COUNT)
        }.join()
        return lastRecentProduct
    }

    override fun save(productId: Long) {
        thread {
            if (recentProductDao.findOrNull(productId) == null) {
                recentProductDao.insert(RecentProduct(productId = productId, seenDateTime = LocalDateTime.now()))
                return@thread
            }
            recentProductDao.update(productId, LocalDateTime.now())
        }.join()
    }

    companion object {
        private const val FIND_RECENT_PRODUCTS_COUNT = 10
    }
}
