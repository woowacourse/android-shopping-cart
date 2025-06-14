package woowacourse.shopping.data.source.products.recentlyviewed

import woowacourse.shopping.domain.Product
import kotlin.concurrent.thread

class RecentlyViewedStorageImpl(
    private val recentlyViewedDao: RecentlyViewedDao,
) : RecentlyViewedStorage {
    override fun getRecentlyViewed(onResult: (List<Long>) -> Unit) {
        thread {
            onResult(recentlyViewedDao.getRecentlyViewed())
        }
    }

    override fun getLatestViewed(onResult: (Long?) -> Unit) {
        thread {
            onResult(recentlyViewedDao.getLatestViewed())
        }
    }

    override fun insert(product: Product) {
        thread {
            recentlyViewedDao.insert(RecentlyViewedEntity(productId = product.id))
        }
    }

    override fun deleteOldestViewed() {
        thread {
            recentlyViewedDao.deleteOldestViewed()
        }
    }

    override fun getCount(onResult: (Int) -> Unit) {
        thread {
            onResult(recentlyViewedDao.getCount())
        }
    }

    companion object {
        private var instance: RecentlyViewedStorageImpl? = null

        @Synchronized
        fun initialize(recentlyViewedDao: RecentlyViewedDao): RecentlyViewedStorageImpl =
            instance ?: RecentlyViewedStorageImpl(recentlyViewedDao).also { instance = it }
    }
}
