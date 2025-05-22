package woowacourse.shopping.domain

class Page(
    private val initialPage: Int,
    private val pageSize: Int,
) {
    var currentPage: Int = initialPage
        private set

    fun getPageNumber(): Int = currentPage

    fun targetRange(): PagingOffset {
        val offset = (currentPage - 1) * pageSize
        return PagingOffset(offset, pageSize)
    }

    fun moveToNextPage() {
        currentPage++
    }

    fun moveToPreviousPage() {
        if (currentPage > initialPage) {
            currentPage--
        }
    }

    fun resetToLastPageIfEmpty(
        itemSize: Int,
        onResetEvent: () -> Unit,
    ) {
        if (itemSize == 0 && currentPage > initialPage) {
            currentPage--
            onResetEvent()
        }
    }

    fun hasPreviousPage() = currentPage > initialPage
}
