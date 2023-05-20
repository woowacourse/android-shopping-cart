package woowacourse.shopping.data

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

    override fun getMostRecentProduct(): RecentProduct? {
        return if (recentDao.selectAllRecent().size == 1) null
        else recentDao.selectAllRecent()[1]
    }
}
