package woowacourse.shopping.model

import java.time.LocalTime

data class RecentProduct(
    val id: Long,
    val productId: Long,
    val recentTime: LocalTime,
)
