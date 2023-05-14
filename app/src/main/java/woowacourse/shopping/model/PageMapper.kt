package woowacourse.shopping.model

import woowacourse.shopping.domain.DomainPageNumber

fun UiPageNumber.toDomain(): DomainPageNumber = DomainPageNumber(value = value)

fun DomainPageNumber.toUi(): UiPageNumber = UiPageNumber(value = value)
