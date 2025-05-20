package woowacourse.shopping.domain.cart

import java.util.UUID

data class Cart(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val productId: Long,
)
