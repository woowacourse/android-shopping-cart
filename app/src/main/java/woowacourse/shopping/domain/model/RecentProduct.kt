package woowacourse.shopping.domain.model

import java.time.LocalDateTime

data class RecentProduct(
    val product: Product,
    val viewedAt: LocalDateTime = LocalDateTime.now(),
)
