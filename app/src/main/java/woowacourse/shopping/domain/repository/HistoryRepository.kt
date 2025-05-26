package woowacourse.shopping.domain.repository

import woowacourse.shopping.domain.product.Product

interface HistoryRepository {
    fun insert(id: Long)
    fun findMostRecentProduct(): Product?
    fun getRecentProducts(limit: Int = 10): List<Product>
}
