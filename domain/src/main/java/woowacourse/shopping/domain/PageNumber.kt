package woowacourse.shopping.domain

class PageNumber(val value: Int = 1) {
    init {
        require(value >= 1) { throw IllegalArgumentException("페이지는 1보다 작을 수 없습니다.") }
    }

    fun nextPage(): PageNumber = PageNumber(value + 1)

    fun previousPage(): PageNumber {
        if (value > 1) return PageNumber(value - 1)
        return this
    }

    fun setPageNumber(page: Int): PageNumber = PageNumber(page)
}
