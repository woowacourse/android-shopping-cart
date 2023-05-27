package woowacourse.shopping.util

import woowacourse.shopping.Page

abstract class OffsetPaging<T>(startPage: Int) {
    abstract val limit: Int
    abstract fun isPlusPageAble(): Boolean
    abstract fun isMinusPageAble(): Boolean
    abstract fun loadPageItems(page: Page): List<T>

    var currentPage = Page(startPage)

    fun setPage(newPage: Page) {
        currentPage = newPage
    }
}
