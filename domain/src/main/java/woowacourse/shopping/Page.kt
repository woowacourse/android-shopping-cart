package woowacourse.shopping

class Page(val value: Int) {

    init {
        require(value >= MINIMUM_PAGE) { PAGE_VALUE_ERROR }
    }

    operator fun plus(number: Int): Page = Page(value + number)

    operator fun minus(number: Int): Page {
        val result = value - number
        if (result <= 0) return Page(value)
        return Page(result)
    }

    fun getOffset(limit: Int): Int {
        return (value - MINIMUM_PAGE) * limit
    }

    companion object {
        private const val MINIMUM_PAGE = 1
        private const val PAGE_VALUE_ERROR = "페이지는 최소 1 이상이어야 합니다"
    }
}
