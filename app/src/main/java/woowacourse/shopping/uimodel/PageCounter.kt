package woowacourse.shopping.uimodel

class PageCounter {
    private var _count = MINIMUM_PAGE_COUNT
    val count
        get() = _count

    fun add(): Int {
        return ++_count
    }

    fun sub(): Int {
        _count = (--_count).coerceAtLeast(MINIMUM_PAGE_COUNT)
        return _count
    }

    companion object {
        private const val MINIMUM_PAGE_COUNT = 1
    }
}