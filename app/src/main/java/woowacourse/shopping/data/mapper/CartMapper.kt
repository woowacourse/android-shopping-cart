package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataCart
import woowacourse.shopping.domain.model.DomainCart

fun DataCart.toDomain(): DomainCart = DomainCart(
    items = cartProducts.map { it.toDomain() },
)

fun DomainCart.toData(): DataCart = DataCart(
    cartProducts = items.map { it.toData() },
)
