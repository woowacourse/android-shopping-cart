package woowacourse.shopping.data.recent

import woowacourse.shopping.data.toEntity
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentItem
import java.time.LocalDateTime
import java.time.ZoneId
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

    override fun insert(product: Product) {
        thread {
            val time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val recentProduct = RecentItem(product.id, product.name, product.imageUrl, time)
            recentProductDao.insert(recentProduct.toEntity())
        }
    }

    override fun clear() {
        thread {
            recentProductDao.clear()
        }
    }
}
