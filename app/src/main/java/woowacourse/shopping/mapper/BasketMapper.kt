package woowacourse.shopping.mapper

import woowacourse.shopping.domain.model.DomainCart
import woowacourse.shopping.model.UiCart

fun UiCart.toDomain(): DomainCart = DomainCart(
    items = cartProducts.map { it.toDomain() },
)

fun DomainCart.toUi(): UiCart = UiCart(
    cartProducts = items.map { it.toUi() },
)
