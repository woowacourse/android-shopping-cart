package woowacourse.shopping.model

import woowacourse.shopping.domain.model.DomainPage

fun UiPage.toDomain(): DomainPage = DomainPage(value = value)

fun DomainPage.toUi(): UiPage = UiPage(value = value)
