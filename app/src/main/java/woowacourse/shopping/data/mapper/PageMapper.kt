package woowacourse.shopping.data.mapper

import woowacourse.shopping.data.model.DataPage
import woowacourse.shopping.domain.model.page.DomainPage

fun DomainPage.toData(extraSize: Int = 0): DataPage =
    DataPage(value = value, sizePerPage = sizePerPage + extraSize)
