package woowacourse.shopping.data.recentproduct

import com.example.domain.Product
import com.example.domain.RecentProduct
import com.example.domain.repository.RecentProductRepository
import java.time.LocalDateTime

class RecentProductRepositoryImpl(
    private val recentProductDao: RecentProductDao
) : RecentProductRepository {

    override fun getAll(): List<RecentProduct> {
        return recentProductDao.getAll()
    }

    override fun getMostRecentProduct(): RecentProduct? {
        return recentProductDao.getMostRecentProduct()
    }

    override fun getRecentProduct(productId: Int): RecentProduct? {
        return recentProductDao.getRecentProduct(productId)
    }

    override fun addRecentProduct(product: Product, viewedDateTime: LocalDateTime) {
        recentProductDao.addColumn(product, viewedDateTime)
    }
}
