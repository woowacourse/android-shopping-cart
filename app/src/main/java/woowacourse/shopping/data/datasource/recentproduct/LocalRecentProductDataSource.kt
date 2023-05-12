package woowacourse.shopping.data.datasource.recentproduct

import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDao
import woowacourse.shopping.data.model.DataRecentProduct

class LocalRecentProductDataSource(private val dao: RecentProductDao) :
    RecentProductDataSource.Local {

    override fun getPartially(size: Int): List<DataRecentProduct> = dao.getPartially(size)

    override fun add(product: DataRecentProduct) {
        while (dao.getSize() >= STORED_DATA_SIZE) {
            dao.removeLast()
        }
        dao.add(product)
    }

    companion object {
        private const val STORED_DATA_SIZE = 50
    }
}
