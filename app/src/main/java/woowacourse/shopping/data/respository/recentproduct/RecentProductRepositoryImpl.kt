package woowacourse.shopping.data.respository.recentproduct

import woowacourse.shopping.data.database.RecentProductDao
import woowacourse.shopping.data.mapper.toUIModel
import woowacourse.shopping.presentation.model.RecentProductModel

class RecentProductRepositoryImpl(
    private val recentProductDao: RecentProductDao
) : RecentProductRepository {

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
