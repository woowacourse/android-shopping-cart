package woowacourse.shopping.data.datasource.basket.local

import woowacourse.shopping.data.database.dao.basket.BasketDao
import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.model.DataBasketProduct
import woowacourse.shopping.data.model.DataProduct

class LocalBasketDataSource(private val dao: BasketDao) : BasketDataSource.Local {
    override fun getPreviousPartially(
        size: Int,
        standard: Int,
        includeStandard: Boolean
    ): List<DataBasketProduct> =
        if (includeStandard) dao.getPreviousPartiallyIncludeStartId(size, standard)
        else dao.getPreviousPartiallyNotIncludeStartId(size, standard)

    override fun getNextPartially(
        size: Int,
        standard: Int,
        includeStandard: Boolean
    ): List<DataBasketProduct> =
        if (includeStandard) dao.getPartiallyIncludeStartId(size, standard)
        else dao.getPartiallyNotIncludeStartId(size, standard)

    override fun add(basketProduct: DataProduct) {
        dao.add(basketProduct)
    }

    override fun remove(basketProduct: DataBasketProduct) {
        dao.remove(basketProduct)
    }
}
