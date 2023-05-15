package woowacourse.shopping.data.database.dao.recentproduct

import woowacourse.shopping.data.model.DataProduct
import woowacourse.shopping.data.model.DataRecentProduct

interface RecentProductDao {
    fun getSize(): Int
    fun getPartially(size: Int): List<DataRecentProduct>
    fun add(recentProduct: DataProduct)
    fun removeLast()
}
