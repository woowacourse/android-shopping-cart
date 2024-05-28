package woowacourse.shopping.data.model.local

import java.time.LocalDateTime

data class CartProductDto(
    val productId: Long,
    val name: String,
    val price: Int,
    val quantity: Int,
    val imageUrl: String,
    val createAt: LocalDateTime,
)
