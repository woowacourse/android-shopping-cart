package woowacourse.shopping.database.product

import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.utils.MockData

object ProductRepositoryImpl : ProductRepository {

    private val products: Map<Long, Product> = MockData.getProductList().associateBy { it.id }

    override fun findAll(): List<Product> {
        return products.values.toList()
    }

    override fun findAll(limit: Int, offset: Int): List<Product> {
        return products.values.toList().subList(offset, offset + limit)
    }

    override fun findById(id: Long): Product? {
        return products[id]
    }
}
