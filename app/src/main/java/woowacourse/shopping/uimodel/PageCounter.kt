package woowacourse.shopping.uimodel

class PageCounter {
    private var count = MINIMUM_PAGE_COUNT

    fun add(): Int {
        return ++count
    }

    fun sub(): Int {
        count = (--count).coerceAtLeast(MINIMUM_PAGE_COUNT)
        return count
    }

    companion object {
        private const val MINIMUM_PAGE_COUNT = 1
    }
}
