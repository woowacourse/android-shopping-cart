package woowacourse.shopping.database.recentProduct

import com.domain.model.Product
import com.domain.model.RecentRepository

class RecentRepositoryImpl(private val recentProductDao: RecentProductDao) : RecentRepository {

    override fun insert(product: Product) {
        recentProductDao.insert(product)
    }

    override fun getRecent(size: Int): List<Product> {
        return recentProductDao.getRecent(size)
    }

    override fun findById(id: Int): Product? {
        return recentProductDao.findById(id)
    }

    override fun delete(id: Int) {
        recentProductDao.delete(id)
    }
}
