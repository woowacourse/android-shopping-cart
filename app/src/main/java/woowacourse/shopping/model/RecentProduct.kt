package woowacourse.shopping.model

import java.time.LocalTime

data class RecentProduct(
    val id: Long = 0,
    val productId: Long,
    val recentTime: LocalTime,
)
