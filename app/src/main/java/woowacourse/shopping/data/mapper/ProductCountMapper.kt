package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataProductCount
import woowacourse.shopping.domain.ProductCount

fun DataProductCount.toDomain(): ProductCount =
    ProductCount(value)

fun ProductCount.toData(): DataProductCount =
    DataProductCount(value)
