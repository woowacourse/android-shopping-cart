package woowacourse.shopping.repository

import woowacourse.shopping.model.Products
import java.util.UUID

interface RecentItemRepository {
    suspend fun getRecentItems(): Products

    suspend fun add(productId: UUID)
}
