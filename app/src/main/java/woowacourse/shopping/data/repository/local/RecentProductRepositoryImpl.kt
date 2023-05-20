package woowacourse.shopping.data.repository.local

import com.example.domain.model.RecentProduct
import com.example.domain.repository.RecentProductRepository
import woowacourse.shopping.data.sql.recent.RecentDao

class RecentProductRepositoryImpl(
    private val recentDao: RecentDao
) : RecentProductRepository {
    override fun getAll(): List<RecentProduct> {
        return recentDao.selectAllRecent()
    }

    override fun addRecentProduct(recentProduct: RecentProduct) {
        recentDao.putRecentProduct(recentProduct)
    }
}
