package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.Quantity
import java.util.UUID

data class Cart(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val quantity: Quantity,
    val productId: Long,
)
