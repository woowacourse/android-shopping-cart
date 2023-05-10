package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.Product

interface RecentProductRepository {
    fun add()
    fun getAll(): List<Product>
}
