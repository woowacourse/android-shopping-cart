package woowacourse.shopping.mapper

import woowacourse.shopping.domain.DomainPageNumber
import woowacourse.shopping.model.UiPageNumber

fun UiPageNumber.toDomain(sizePerPage: Int): DomainPageNumber =
    DomainPageNumber(value = value, sizePerPage = sizePerPage)

fun DomainPageNumber.toUi(): UiPageNumber =
    UiPageNumber(value = value)
