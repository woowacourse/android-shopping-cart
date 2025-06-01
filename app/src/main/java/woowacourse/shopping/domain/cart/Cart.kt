package woowacourse.shopping.domain.cart

import woowacourse.shopping.domain.Quantity

data class Cart(
    val quantity: Quantity,
    val productId: Long,
)
