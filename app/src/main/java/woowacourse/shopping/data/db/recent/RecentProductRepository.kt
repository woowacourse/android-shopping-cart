package woowacourse.shopping.data.db.recent

import woowacourse.shopping.domain.model.Product
import woowacourse.shopping.domain.model.RecentProduct

interface RecentProductRepository {
    fun save(product: Product)

    fun update(productId: Long)

    fun findOrNullByProductId(productId: Long): RecentProduct?

    fun findMostRecentProduct(): RecentProduct?

    fun findAll(limit: Int): List<RecentProduct>

    fun deleteAll()
}
