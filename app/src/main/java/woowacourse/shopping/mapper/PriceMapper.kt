package woowacourse.shopping.mapper

import woowacourse.shopping.domain.Price
import woowacourse.shopping.model.UiPrice

fun UiPrice.toDomain(): Price =
    Price(value)

fun Price.toUi(): UiPrice =
    UiPrice(value)
