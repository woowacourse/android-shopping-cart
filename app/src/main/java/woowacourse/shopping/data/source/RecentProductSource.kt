package woowacourse.shopping.data.source

import woowacourse.shopping.data.source.local.recent.RecentProductEntity

interface RecentProductSource {
    suspend fun getRecentProductIds(): List<RecentProductEntity>

    suspend fun addRecentProductId(productId: String)

    suspend fun getLastViewProductId(): RecentProductEntity?
}
