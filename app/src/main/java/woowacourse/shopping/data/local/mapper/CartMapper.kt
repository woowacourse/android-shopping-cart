package woowacourse.shopping.data.local.mapper

import woowacourse.shopping.data.local.entity.CartEntity
import woowacourse.shopping.domain.Cart

fun Cart.toEntity(): CartEntity {
    return CartEntity(
        productId,
        quantity,
    )
}

fun CartEntity.toDomain(): Cart {
    return Cart(
        productId,
        quantity,
    )
}
