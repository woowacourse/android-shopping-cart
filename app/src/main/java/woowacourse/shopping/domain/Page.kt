package woowacourse.shopping.domain

class Page(
    private val initialPage: Int,
    private val pageSize: Int,
) {
    private var currentPage: Int = initialPage

    fun getPageNumber(): Int = currentPage

    fun targetRange(itemSize: Int): IntRange {
        val start = (currentPage - 1) * pageSize
        val end = minOf(start + pageSize, itemSize) - 1
        return start..end
    }

    fun moveToNextPage() {
        currentPage++
    }

    fun moveToPreviousPage() {
        if (currentPage > initialPage) {
            currentPage--
        }
    }

    fun resetToLastPageIfEmpty(itemSize: Int): Boolean {
        if (itemSize == 0 && currentPage > initialPage) {
            currentPage--
            return true
        }
        return false
    }

    fun hasPreviousPage() = currentPage > initialPage

    fun hasNextPage(itemSize: Int): Boolean {
        val fromIndex = currentPage * pageSize
        return fromIndex < itemSize
    }

    fun isLastPage(itemSize: Int) = currentPage * pageSize >= itemSize
}
