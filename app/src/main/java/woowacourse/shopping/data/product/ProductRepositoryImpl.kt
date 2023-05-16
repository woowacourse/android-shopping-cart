package woowacourse.shopping.data.product

import woowacourse.shopping.data.database.dao.ProductDao
import woowacourse.shopping.domain.Products
import woowacourse.shopping.domain.repository.ProductRepository

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {
    override fun getProducts(startIndex: Int, size: Int): Products {
        return productDao.selectByRange(start = startIndex, range = size)
    }
}
