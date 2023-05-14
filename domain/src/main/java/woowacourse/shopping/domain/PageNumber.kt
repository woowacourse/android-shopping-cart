package woowacourse.shopping.domain

typealias DomainPageNumber = PageNumber

data class PageNumber(
    val value: Int = DEFAULT_PAGE,
    val sizePerPage: Int = DEFAULT_SIZE_PER_PAGE,
) {
    fun hasPrevious(): Boolean = value > MIN_PAGE

    operator fun inc(): PageNumber =
        copy(value = value + 1)

    operator fun dec(): PageNumber =
        copy(value = (value - 1).coerceAtLeast(MIN_PAGE))

    companion object {
        private const val DEFAULT_PAGE = 1
        private const val DEFAULT_SIZE_PER_PAGE = 5
        private const val MIN_PAGE = 1
    }
}
