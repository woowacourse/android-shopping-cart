package woowacourse.shopping.data.recentproduct

import woowacourse.shopping.data.database.dao.RecentProductDao
import woowacourse.shopping.domain.Product
import woowacourse.shopping.domain.RecentProduct
import woowacourse.shopping.domain.RecentProducts
import woowacourse.shopping.domain.repository.RecentProductRepository

class RecentProductRepositoryImpl(
    private val recentProductDao: RecentProductDao
) : RecentProductRepository {
    override fun addRecentProduct(recentProduct: RecentProduct) {
        recentProductDao.insertRecentProduct(recentProduct)
    }

    override fun getAll(): RecentProducts {
        return recentProductDao.selectAll()
    }

    override fun getByProduct(product: Product): RecentProduct? {
        return recentProductDao.selectByProduct(product)
    }

    override fun modifyRecentProduct(recentProduct: RecentProduct) {
        recentProductDao.updateRecentProduct(recentProduct)
    }

    override fun getLatestRecentProduct(): RecentProduct? {
        return recentProductDao.selectLatestRecentProduct()
    }
}
