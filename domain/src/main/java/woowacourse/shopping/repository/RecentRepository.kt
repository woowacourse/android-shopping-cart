package woowacourse.shopping.repository

import woowacourse.shopping.model.Product
import woowacourse.shopping.model.RecentProduct

interface RecentRepository {
    fun insert(product: Product)
    fun getRecent(maxSize: Int): List<RecentProduct>
    fun delete(id: Int)
    fun findById(id: Int): RecentProduct?
}
