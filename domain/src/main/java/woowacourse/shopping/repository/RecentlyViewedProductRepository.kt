package woowacourse.shopping.repository

import woowacourse.shopping.domain.Product

interface RecentlyViewedProductRepository {
    fun findAll(): List<Product>
    fun save(product: Product)
}
