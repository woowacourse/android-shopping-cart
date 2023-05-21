package woowacourse.shopping.mapper

import woowacourse.shopping.domain.model.DomainPage
import woowacourse.shopping.model.UiPage

fun UiPage.toDomain(sizePerPage: Int): DomainPage =
    DomainPage(value = value, sizePerPage = sizePerPage)

fun DomainPage.toUi(): UiPage =
    UiPage(value = value)
