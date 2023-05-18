package woowacourse.shopping.mapper

import woowacourse.shopping.domain.ProductCount
import woowacourse.shopping.model.UiProductCount

fun UiProductCount.toDomain(): ProductCount =
    ProductCount(value)

fun ProductCount.toUi(): UiProductCount =
    UiProductCount(value)
