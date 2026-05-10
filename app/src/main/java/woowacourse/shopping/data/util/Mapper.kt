package woowacourse.shopping.data.util

import woowacourse.shopping.data.local.CartEntity
import woowacourse.shopping.domain.CartItem
import woowacourse.shopping.domain.CartItems

fun CartEntity.toDomain(): CartItem =
    CartItem(
        productId = productId,
        amount = amount,
    )

fun List<CartEntity>.toDomain(isLast: Boolean): CartItems =
    CartItems(
        items = map { it.toDomain() },
        isLast = isLast,
    )
