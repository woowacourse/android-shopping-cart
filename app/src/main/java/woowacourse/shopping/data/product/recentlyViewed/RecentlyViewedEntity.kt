package woowacourse.shopping.data.product.recentlyViewed

import java.time.LocalDateTime

data class RecentlyViewedEntity(
    val id: Long,
    val productId: Long,
    val viewedDateTime: LocalDateTime,
)
