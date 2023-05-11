package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.database.dao.product.ProductDao
import woowacourse.shopping.data.model.DataProduct

class LocalProductDataSource(private val dao: ProductDao) : ProductDataSource.Local {
    override fun getAll(): List<DataProduct> = dao.getAll()
}
