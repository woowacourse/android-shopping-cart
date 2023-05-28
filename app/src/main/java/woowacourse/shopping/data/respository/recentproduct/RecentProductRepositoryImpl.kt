package woowacourse.shopping.data.respository.recentproduct

import android.content.Context
import woowacourse.shopping.data.database.RecentProductDao
import woowacourse.shopping.data.model.RecentProductEntity

class RecentProductRepositoryImpl(
    context: Context
) : RecentProductRepository {
    private val recentProductDao = RecentProductDao(context)

    override fun getRecentProducts(limit: Int): List<RecentProductEntity> {
        return recentProductDao.getAll(limit)
    }

    override fun deleteNotTodayRecentProducts(today: String) {
        recentProductDao.deleteNotToday(today)
    }

    override fun addCart(productId: Long) {
        recentProductDao.insertRecentProduct(productId)
    }
}
