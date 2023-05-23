package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataProductCount
import woowacourse.shopping.domain.model.ProductCount

fun DataProductCount.toDomain(): ProductCount =
    ProductCount(value)

fun ProductCount.toData(): DataProductCount =
    DataProductCount(value)
