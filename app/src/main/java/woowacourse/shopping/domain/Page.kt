package woowacourse.shopping.domain


data class PageRequest(
    val index: Int,
    val size: Int,
)

data class Page<T>(
    val items: List<T>,
    val pageIndex: Int,
    val pageSize: Int,
    val totalCount: Int,
) {
    val hasNext: Boolean
        get() = (pageIndex + 1) * pageSize < totalCount
}

fun <T> List<T>.toPage(request: PageRequest): Page<T> {
    val fromIndex = request.index * request.size
    val toIndex = minOf(fromIndex + request.size, size)
    val pageItems = if (fromIndex >= size) emptyList() else subList(fromIndex, toIndex)

    return Page(
        items = pageItems,
        pageIndex = request.index,
        pageSize = request.size,
        totalCount = size,
    )
}