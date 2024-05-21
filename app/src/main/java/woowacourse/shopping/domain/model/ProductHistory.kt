package woowacourse.shopping.domain.model

import java.time.LocalDateTime

data class ProductHistory(
    val productId: Long,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val createAt: LocalDateTime,
)
