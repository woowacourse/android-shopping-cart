package woowacourse.shopping.data.page

data class PageRequest(
    val pageSize: Int,
    val requestPage: Int,
) {
    val offset = requestPage * pageSize

    fun <T> toPage(
        items: List<T>,
        totalCounts: Int,
    ): Page<T> {
        return Page(
            items = items,
            currentPage = requestPage,
            totalCounts = totalCounts,
            pageSize = pageSize,
        )
    }
}
