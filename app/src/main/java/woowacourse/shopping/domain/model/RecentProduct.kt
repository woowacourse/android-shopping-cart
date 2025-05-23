package woowacourse.shopping.domain.model

import java.time.LocalDateTime

data class RecentProduct(
    val id: Long? = null,
    val product: Product,
    val viewedAt: LocalDateTime = LocalDateTime.now(),
)
