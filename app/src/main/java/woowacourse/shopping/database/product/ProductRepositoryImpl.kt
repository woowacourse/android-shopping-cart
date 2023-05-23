package woowacourse.shopping.database.product

import woowacourse.shopping.datasource.product.ProductDataSource
import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.ProductRepository

class ProductRepositoryImpl(private val dataSource: ProductDataSource) : ProductRepository {

    override fun findAll(limit: Int, offset: Int): List<Product> {
        return dataSource.findAll(limit, offset)
    }

    override fun countAll(): Int {
        return dataSource.countAll()
    }

    override fun findById(id: Long): Product? {
        return dataSource.findById(id)
    }
}