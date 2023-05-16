package woowacourse.shopping.mapper

import woowacourse.shopping.domain.DomainProducts
import woowacourse.shopping.model.UiProducts

fun UiProducts.toDomain(loadUnit: Int): DomainProducts = DomainProducts(
    items = getItems().map { it.toDomain() },
    loadUnit = loadUnit,
)

fun DomainProducts.toUi(): UiProducts = UiProducts(
    items = getItems().map { it.toUi() },
)
