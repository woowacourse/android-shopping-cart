package woowacourse.shopping.data.recentproduct

import com.example.domain.RecentProduct
import com.example.domain.repository.RecentProductRepository
import java.time.LocalDateTime

class RecentProductRepositoryImpl(
    private val recentProductDao: RecentProductDao
) : RecentProductRepository {

    override fun getAll(): List<RecentProduct> {
        return recentProductDao.getAll()
    }

    override fun addRecentProduct(productId: Int, viewedDateTime: LocalDateTime) {
        recentProductDao.addColumn(productId, viewedDateTime)
    }
}
