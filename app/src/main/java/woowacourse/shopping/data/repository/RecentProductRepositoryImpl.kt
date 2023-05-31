package woowacourse.shopping.data.repository

import com.shopping.domain.RecentProduct
import com.shopping.repository.RecentProductsRepository
import woowacourse.shopping.data.db.RecentProductDao

class RecentProductRepositoryImpl(
    private val recentProductDao: RecentProductDao
) : RecentProductsRepository {
    override fun getAll(): List<RecentProduct> {
        return recentProductDao.getAll()
    }

    override fun getFirst(): RecentProduct? {
        if (recentProductDao.isEmpty()) {
            return null
        }
        return recentProductDao.getFirst()
    }

    override fun isEmpty(): Boolean {
        return recentProductDao.isEmpty()
    }

    override fun insert(recentProduct: RecentProduct) {
        recentProductDao.insert(recentProduct)
    }

    override fun remove(recentProduct: RecentProduct) {
        recentProductDao.remove(recentProduct)
    }

    companion object {
        private const val NULL_ERROR = "찾는 값이 없습니다."
    }
}
