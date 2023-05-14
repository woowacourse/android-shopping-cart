package woowacourse.shopping.repository

import woowacourse.shopping.model.Product

interface RecentRepository {
    fun insert(product: Product)
    fun getRecent(maxSize: Int): List<Product>
    fun delete(id: Int)
    fun findById(id: Int): Product?
}
