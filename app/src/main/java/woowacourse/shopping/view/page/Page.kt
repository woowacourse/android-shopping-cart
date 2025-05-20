package woowacourse.shopping.view.page

import kotlin.math.min

data class Page<T>(
    val items: List<T>,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
    val currentPage: Int,
) {
    companion object {
        fun <T> from(
            items: List<T>,
            totalCounts: Int,
            requestPage: Int,
            pageSize: Int,
        ): Page<T> {
            val from = requestPage * pageSize
            val until = min(from + pageSize, totalCounts)
            return Page(
                items = items,
                hasPrevious = requestPage > 0,
                hasNext = until < totalCounts,
                requestPage,
            )
        }
    }
}
