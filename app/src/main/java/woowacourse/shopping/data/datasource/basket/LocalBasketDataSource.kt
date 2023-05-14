package woowacourse.shopping.data.datasource.basket

import woowacourse.shopping.data.database.dao.basket.BasketDao
import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.data.model.DataProduct

class LocalBasketDataSource(private val dao: BasketDao) : BasketDataSource.Local {
    override fun getPartially(page: DataPageNumber): List<DataProduct> =
        dao.getPartially(page)

    override fun add(product: DataProduct) {
        dao.add(product)
    }

    override fun remove(product: DataProduct) {
        dao.remove(product)
    }
}
