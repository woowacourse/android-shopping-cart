package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

typealias DomainRecentProductRepository = RecentProductRepository

interface RecentProductRepository {
    fun add(product: Product)
    fun getPartially(size: Int): List<Product>
}
