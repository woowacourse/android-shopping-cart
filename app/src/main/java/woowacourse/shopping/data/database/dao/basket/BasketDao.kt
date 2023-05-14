package woowacourse.shopping.data.database.dao.basket

import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.DataProduct

interface BasketDao {
    fun getPartially(page: DataPageNumber): List<DataProduct>
    fun add(product: DataProduct)
    fun remove(product: DataProduct)
}
