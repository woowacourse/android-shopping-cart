package woowacourse.shopping.domain

import java.time.LocalDateTime

data class RecentProduct(
    val product: Product,
    val viewTime: LocalDateTime,
)
