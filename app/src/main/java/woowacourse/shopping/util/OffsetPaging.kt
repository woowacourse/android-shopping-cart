package woowacourse.shopping.util

import woowacourse.shopping.Page

abstract class OffsetPaging<T>(startPage: Int) {
    abstract val limit: Int
    abstract fun isPlusPageAble(): Boolean
    abstract fun isMinusPageAble(): Boolean
    abstract fun loadPageItems(page: Page): List<T>

    private val _currentPage: SafeMutableLiveData<Page> = SafeMutableLiveData(Page(startPage))
    val currentPage: SafeLiveData<Page> get() = _currentPage

    fun setPage(page: Page) {
        _currentPage.value = page
    }
}
