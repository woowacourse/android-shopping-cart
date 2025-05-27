package woowacourse.shopping.domain

import java.time.LocalDateTime

class RecentProduct(
    val product: Product,
    val createdDateTime: LocalDateTime,
)
