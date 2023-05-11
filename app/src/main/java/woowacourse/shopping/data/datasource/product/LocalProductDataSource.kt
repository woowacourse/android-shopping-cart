package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.database.dao.product.ProductDao
import woowacourse.shopping.data.model.DataProduct

class LocalProductDataSource(private val dao: ProductDao) : ProductDataSource.Local {
    override fun getPartially(size: Int): List<DataProduct> = dao.getPartially(size)
}
