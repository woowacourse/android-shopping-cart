package woowacourse.shopping.data.recent

import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.RecentItem
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(private val recentProductDao: RecentProductDao) :
    RecentProductRepository {
    override fun getMostRecent(
        count: Int,
        onSuccess: (List<RecentItem>) -> Unit,
    ) {
        thread {
            val recentProducts = recentProductDao.getMostRecent(count)
            onSuccess(recentProducts)
        }
    }

    override fun getLastProductBefore(
        productId: Int,
        onResult: (RecentItem?) -> Unit,
    ) {
        thread {
            val lastProduct = recentProductDao.getLastProductBefore(productId)
            onResult(lastProduct)
        }
    }

    override fun insert(recentProduct: RecentItem) {
        thread {
            recentProductDao.insert(recentProduct.toEntity())
        }
    }

    override fun clear() {
        thread {
            recentProductDao.clear()
        }
    }
}
