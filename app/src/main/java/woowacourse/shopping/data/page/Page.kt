package woowacourse.shopping.data.page

data class Page<T>(
    val items: List<T>,
    val totalCounts: Int,
    val currentPage: Int,
    val pageSize: Int,
) {
    val hasPrevious = currentPage > 0
    val hasNext = totalCounts > (currentPage + 1) * pageSize
}
