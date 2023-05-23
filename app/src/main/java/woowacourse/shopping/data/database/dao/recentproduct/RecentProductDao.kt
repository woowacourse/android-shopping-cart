package woowacourse.shopping.data.database.dao.recentproduct

import woowacourse.shopping.data.model.DataRecentProduct

interface RecentProductDao {
    fun getSize(): Int
    fun getRecentProductsPartially(size: Int): List<DataRecentProduct>
    fun addRecentProduct(item: DataRecentProduct)
    fun removeLast()
}
