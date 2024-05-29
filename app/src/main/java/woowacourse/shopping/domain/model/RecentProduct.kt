package woowacourse.shopping.domain.model

import java.time.LocalDateTime

data class RecentProduct(
    val productId: Long,
    val productName: String,
    val imageUrl: String,
    val dateTime: LocalDateTime,
)
