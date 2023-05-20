package woowacourse.shopping.mapper

import woowacourse.shopping.domain.DomainBasket
import woowacourse.shopping.model.UiBasket

fun UiBasket.toDomain(loadUnit: Int): DomainBasket = DomainBasket(
    basketProducts = basketProducts.map { it.toDomain() },
    loadUnit = loadUnit,
)

fun DomainBasket.toUi(): UiBasket = UiBasket(
    basketProducts = basketProducts.map { it.toUi() },
)
