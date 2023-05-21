package woowacourse.shopping.mapper

import woowacourse.shopping.domain.model.DomainCart
import woowacourse.shopping.model.UiCart

fun UiCart.toDomain(loadUnit: Int): DomainCart = DomainCart(
    items = cartProducts.map { it.toDomain() },
    loadUnit = loadUnit,
)

fun DomainCart.toUi(): UiCart = UiCart(
    cartProducts = items.map { it.toUi() },
)
