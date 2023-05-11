package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.database.dao.product.ProductDao
import woowacourse.shopping.data.datasource.basket.BasketDataSource
import woowacourse.shopping.data.model.DataProduct

class LocalProductDataSource(private val dao: ProductDao) : BasketDataSource.Local {
    override fun getAll(): List<DataProduct> = dao.getAll()
}
