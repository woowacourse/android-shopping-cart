package woowacourse.shopping.data.datasource.recentproduct

import woowacourse.shopping.data.database.dao.recentproduct.RecentProductDao
import woowacourse.shopping.data.model.DataProduct

class LocalRecentProductDataSource(private val dao: RecentProductDao) :
    RecentProductDataSource.Local {

    override fun getPartially(size: Int): List<DataProduct> = dao.getPartially(size)

    override fun add(product: DataProduct) {
        while (dao.getSize() >= STORED_DATA_SIZE) {
            dao.removeLast()
        }
        dao.add(product)
    }

    companion object {
        private const val STORED_DATA_SIZE = 50
    }
}
