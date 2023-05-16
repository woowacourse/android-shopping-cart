package woowacourse.shopping.model

data class PageUiModel(
    val allSize: Int,
    val currentPage: Int
) {
    private val pageCount: Int
        get() {
            return kotlin.math.ceil((allSize.toDouble() / PAGE_LOAD_SIZE)).toInt()
        }

    fun hasPreviousPage(): Boolean {
        return currentPage > 1
    }

    fun hasNextPage(): Boolean {
        return currentPage < pageCount
    }

    companion object {
        const val PAGE_LOAD_SIZE = 5
    }
}

