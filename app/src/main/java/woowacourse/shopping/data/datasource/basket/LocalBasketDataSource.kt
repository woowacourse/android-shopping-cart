package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.database.dao.basket.BasketDao
import woowacourse.shopping.data.model.DataProduct

class LocalBasketDataSource(private val dao: BasketDao) : BasketDataSource.Local {
    override fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<DataProduct> =
        dao.getPartially(size, lastId, isNext)

    override fun add(product: DataProduct) {
        dao.add(product)
    }

    override fun remove(product: DataProduct) {
        dao.remove(product)
    }
}
