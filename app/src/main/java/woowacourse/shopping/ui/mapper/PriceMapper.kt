package woowacourse.shopping.ui.mapper

import woowacourse.shopping.domain.Price
import woowacourse.shopping.ui.model.UiPrice

fun UiPrice.toDomain(): Price =
    Price(value)

fun Price.toUi(): UiPrice =
    UiPrice(value)
