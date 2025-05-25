package woowacourse.shopping.data.datasource

import woowacourse.shopping.data.dao.RecentProductDao
import woowacourse.shopping.data.entity.RecentProductEntity

class RecentProductLocalDataSourceImpl(private val recentProductDao: RecentProductDao) :
    RecentProductLocalDataSource {
    override fun insert(productEntity: RecentProductEntity): Long = recentProductDao.insert(productEntity)

    override fun getById(id: Long): RecentProductEntity = recentProductDao.getById(id)

    override fun getAll(): List<RecentProductEntity> = recentProductDao.getAll()

    override fun deleteLastByCreatedDateTime() {
        recentProductDao.deleteLastByCreatedDateTime()
    }
}
