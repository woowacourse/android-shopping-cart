package woowacourse.shopping.data.recent

import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.RecentProduct
import kotlin.concurrent.thread

class RecentProductRepositoryImpl(private val recentProductDao: RecentProductDao) :
    RecentProductRepository {
    override fun getMostRecent(
        count: Int,
        onSuccess: (List<RecentProduct>) -> Unit,
    ) {
        thread {
            val recentProducts = recentProductDao.getMostRecent(count)
            onSuccess(recentProducts)
        }
    }

    override fun getLastProductBefore(
        productId: Int,
        onResult: (RecentProduct?) -> Unit,
    ) {
        thread {
            val lastProduct = recentProductDao.getLastProductBefore(productId)
            onResult(lastProduct)
        }
    }

    override fun insert(recentProduct: RecentProduct) {
        thread {
            recentProductDao.insert(recentProduct.toEntity())
        }
    }
}
