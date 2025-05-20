package woowacourse.shopping.data.page

data class Page<T>(
    val items: List<T>,
    val currentPage: Int,
    val totalCounts: Int,
    val pageSize: Int,
) {
    val hasPrevious = currentPage > 0
    val hasNext = totalCounts > (currentPage + 1) * pageSize
}
