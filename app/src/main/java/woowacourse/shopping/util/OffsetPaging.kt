package woowacourse.shopping.util

import woowacourse.shopping.Page

abstract class OffsetPaging<T> {
    var currentPage: Page = Page(START_PAGE)
    fun isMinusPageAble(): Boolean = currentPage.value != START_PAGE

    abstract val limit: Int
    abstract fun plusPage(): Page
    abstract fun minusPage(): Page
    abstract fun isPlusPageAble(): Boolean
    abstract fun getPageItems(page: Page): List<T>

    companion object {
        private const val START_PAGE = 1
    }
}
