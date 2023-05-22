package woowacourse.shopping.database.product

import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.ProductRepository
import woowacourse.shopping.utils.MockData

object MemoryProductRepository : ProductRepository {

    private val products: Map<Long, Product> = MockData.getProductList().associateBy { it.id }
    override fun findAll(limit: Int, offset: Int): List<Product> {
        return products.values.toList()
            .slice(offset until products.values.size)
            .take(limit)
    }

    override fun countAll(): Int {
        return products.values.size
    }

    override fun findById(id: Long): Product? {
        return products[id]
    }
}
