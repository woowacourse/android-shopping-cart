package woowacourse.shopping.data.database.dao.basket

import woowacourse.shopping.data.model.DataProduct

interface BasketDao {
    fun getPartially(size: Int): List<DataProduct>
    fun add(product: DataProduct)
    fun remove(product: DataProduct)
}
