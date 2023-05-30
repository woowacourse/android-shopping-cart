package woowacourse.shopping.util

import woowacourse.shopping.Page

abstract class OffsetPaging<T>(startPage: Int) {
    abstract val limit: Int
    var currentPage = Page(startPage)

    fun setPage(newPage: Page) {
        currentPage = newPage
    }
}
