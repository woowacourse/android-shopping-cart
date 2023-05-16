package woowacourse.shopping.database

import woowacourse.shopping.domain.Product
import woowacourse.shopping.repository.RecentlyViewedProductRepository

object FakeRecentlyViewedProductRepository : RecentlyViewedProductRepository {
    private val products = mutableMapOf<Long, Product>()

    override fun findAll(): List<Product> {
        return products.values.toList()
    }

    override fun save(product: Product) {
        products[product.id] = product
    }
}
