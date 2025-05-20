package woowacourse.shopping.data.ext

import woowacourse.shopping.data.page.PageRequest
import kotlin.math.min

fun <T> List<T>.subList(pageRequest: PageRequest): List<T> {
    return subList(
        pageRequest.offset,
        min(pageRequest.offset + pageRequest.pageSize, this.size),
    )
}
