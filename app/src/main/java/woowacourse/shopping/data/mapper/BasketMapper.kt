package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataBasket
import woowacourse.shopping.domain.DomainBasket

fun DataBasket.toDomain(loadUnit: Int): DomainBasket = DomainBasket(
    basketProducts = basketProducts.map { it.toDomain() },
    loadUnit = loadUnit,
)

fun DomainBasket.toData(): DataBasket = DataBasket(
    basketProducts = basketProducts.map { it.toData() },
)
