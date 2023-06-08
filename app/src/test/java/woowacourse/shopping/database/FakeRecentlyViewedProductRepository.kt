package woowacourse.shopping.database

import woowacourse.shopping.domain.RecentlyViewedProduct
import woowacourse.shopping.repository.RecentlyViewedProductRepository

object FakeRecentlyViewedProductRepository : RecentlyViewedProductRepository {
    private val products = mutableMapOf<Long, RecentlyViewedProduct>()

    override fun findAll(): List<RecentlyViewedProduct> {
        return products.values.toList()
    }

    override fun findLast(): RecentlyViewedProduct? {
        if (products.isEmpty()) return null
        return products.values.last()
    }

    override fun save(product: RecentlyViewedProduct) {
        products[product.id] = product
    }
}
