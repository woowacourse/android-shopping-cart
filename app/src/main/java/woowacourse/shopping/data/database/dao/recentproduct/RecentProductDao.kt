package woowacourse.shopping.data.database.dao.recentproduct

import woowacourse.shopping.data.model.DataProduct

interface RecentProductDao {
    fun getSize(): Int
    fun getPartially(size: Int): List<DataProduct>
    fun add(product: DataProduct)
    fun removeLast()
}
