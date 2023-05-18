package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataCount
import woowacourse.shopping.domain.Count

fun DataCount.toDomain(): Count =
    Count(value)

fun Count.toData(): DataCount =
    DataCount(value)
