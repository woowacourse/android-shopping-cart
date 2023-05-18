package woowacourse.shopping.data.datasource.product

import woowacourse.shopping.data.database.dao.product.ProductDao
import woowacourse.shopping.data.model.BasketProduct
import woowacourse.shopping.data.model.Product

class LocalProductDataSource(private val dao: ProductDao) : ProductDataSource.Local {
    override fun getPartially(size: Int, lastId: Int): List<Product> =
        dao.getPartially(size, lastId)
}
