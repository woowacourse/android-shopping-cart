package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataPageNumber
import woowacourse.shopping.domain.DomainPageNumber

fun DataPageNumber.toDomain(): DomainPageNumber = DomainPageNumber(value = value)

fun DomainPageNumber.toData(extraSize: Int = 0): DataPageNumber =
    DataPageNumber(value = value, sizePerPage = sizePerPage + extraSize)
