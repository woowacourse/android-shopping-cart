package woowacourse.shopping.mapper

import woowacourse.shopping.domain.DomainCart
import woowacourse.shopping.model.UiCart

fun UiCart.toDomain(loadUnit: Int): DomainCart = DomainCart(
    cartProducts = cartProducts.map { it.toDomain() },
    loadUnit = loadUnit,
)

fun DomainCart.toUi(): UiCart = UiCart(
    cartProducts = cartProducts.map { it.toUi() },
)
