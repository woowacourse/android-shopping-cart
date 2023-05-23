package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataPrice
import woowacourse.shopping.domain.model.Price

fun DataPrice.toDomain(): Price =
    Price(value)

fun Price.toData(): DataPrice =
    DataPrice(value)
