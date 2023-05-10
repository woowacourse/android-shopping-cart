package woowacourse.shopping.data.database.dao.product

import woowacourse.shopping.data.model.DataProduct

interface RecentProductDao {
    fun getAll(): List<DataProduct>
    fun getPartially(size: Int): List<DataProduct>
    fun add(product: DataProduct)
    fun removeLast()
}
