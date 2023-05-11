package woowacourse.shopping.data.database.dao.basket

import woowacourse.shopping.data.model.DataProduct

interface BasketDao {
    fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<DataProduct>
    fun add(product: DataProduct)
    fun remove(product: DataProduct)
}
