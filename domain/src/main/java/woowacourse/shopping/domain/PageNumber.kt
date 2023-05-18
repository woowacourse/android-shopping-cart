package woowacourse.shopping.domain

typealias DomainPageNumber = PageNumber

data class PageNumber(
    val value: Int = DEFAULT_PAGE,
    val sizePerPage: Int = DEFAULT_SIZE_PER_PAGE,
) {
    init {
        require(value >= DEFAULT_PAGE) { INVALID_PAGE_NUMBER_ERROR_MESSAGE }
    }

    fun hasPrevious(): Boolean = value > MIN_PAGE

    fun next(): PageNumber = copy(value = value + 1)

    fun prev(): PageNumber = copy(value = (value - 1).coerceAtLeast(MIN_PAGE))

    companion object {
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_SIZE_PER_PAGE = 5
        private const val MIN_PAGE = 1

        private const val INVALID_PAGE_NUMBER_ERROR_MESSAGE =
            "페이지 번호는 1 이상의 정수만 가능합니다."
    }
}
