package woowacourse.shopping.data.recentproduct

import com.example.domain.RecentProduct
import com.example.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val recentProductDao: RecentProductDao
) : RecentProductRepository {

    override fun getAll(): List<RecentProduct> {
        return recentProductDao.getAll()
    }

    override fun addRecentProduct(recentProduct: RecentProduct) {
        recentProductDao.addColumn(recentProduct)
    }
}
