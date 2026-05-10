package woowacourse.shopping.repository

import woowacourse.shopping.model.Products
import java.util.UUID

interface RecentProductRepository {
    suspend fun getRecentProducts(): Products

    suspend fun add(productId: UUID)
}
