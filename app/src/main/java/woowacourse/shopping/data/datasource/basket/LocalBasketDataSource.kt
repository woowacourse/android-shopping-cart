package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.database.dao.basket.BasketDao
import woowacourse.shopping.data.model.DataBasketProduct

class LocalBasketDataSource(private val dao: BasketDao) : BasketDataSource.Local {
    override fun getPartially(size: Int, lastId: Int, isNext: Boolean): List<DataBasketProduct> =
        dao.getPartially(size, lastId, isNext)

    override fun add(basketProduct: DataBasketProduct) {
        dao.add(basketProduct)
    }

    override fun remove(basketProduct: DataBasketProduct) {
        dao.remove(basketProduct)
    }
}
