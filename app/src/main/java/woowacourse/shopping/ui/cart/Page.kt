package woowacourse.shopping.ui.cart

data class Page(val value: Int) {
    init {
        require(value >= 0) { ERROR_PAGE_BELOW_ZERO }
    }

    fun moveToPreviousPage(): Page = Page(value - 1)

    fun moveToNextPage(): Page = Page(value + 1)

    fun isFirstPage(): Boolean = value == VALUE_FIRST_PAGE

    companion object {
        private const val ERROR_PAGE_BELOW_ZERO = "페이지는 0보다 작을 수 없습니다."
        private const val VALUE_FIRST_PAGE = 0
    }
}
