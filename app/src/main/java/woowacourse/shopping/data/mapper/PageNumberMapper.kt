package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataPage
import woowacourse.shopping.domain.model.DomainPage

fun DataPage.toDomain(): DomainPage = DomainPage(value = value)

fun DomainPage.toData(extraSize: Int = 0): DataPage =
    DataPage(value = value, sizePerPage = sizePerPage + extraSize)
