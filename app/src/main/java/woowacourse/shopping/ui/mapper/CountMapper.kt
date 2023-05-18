package woowacourse.shopping.ui.mapper

import woowacourse.shopping.domain.Count
import woowacourse.shopping.ui.model.UiCount

fun UiCount.toDomain(): Count =
    Count(value)

fun Count.toUi(): UiCount =
    UiCount(value)
