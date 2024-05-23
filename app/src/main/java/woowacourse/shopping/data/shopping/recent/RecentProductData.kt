package woowacourse.shopping.data.shopping.recent

import java.time.LocalDateTime

data class RecentProductData(
    val productId: Long,
    val createdTime: LocalDateTime,
)
