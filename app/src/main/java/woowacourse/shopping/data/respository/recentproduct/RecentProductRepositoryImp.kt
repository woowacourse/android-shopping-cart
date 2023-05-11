package woowacourse.shopping.data.respository.recentproduct

import android.content.Context
import woowacourse.shopping.data.database.RecentProductDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.RecentProductModel

class RecentProductRepositoryImp(
    context: Context
) : RecentProductRepository {
    private val recentProductDao = RecentProductDao(context)

    override fun getRecentProducts(): List<RecentProductModel> {
        return recentProductDao.getAll().map { it.toUIModel() }
    }

    override fun deleteNotTodayRecentProducts(today: String) {
        recentProductDao.deleteNotToday(today)
    }

    override fun addCart(productId: Long) {
        recentProductDao.insertRecentProduct(productId)
    }
}
