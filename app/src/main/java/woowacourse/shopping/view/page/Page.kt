package woowacourse.shopping.view.page

import kotlin.math.min

data class Page <T> (
    val items: List<T>,
    val hasPrevious: Boolean,
    val hasNext: Boolean,
    val currentPage: Int
) {
    operator fun plus(page: Page<T>?): Page<T> {
        page?: return this
        return Page(
            items = this.items + page.items,
            hasPrevious = this.hasPrevious,
            hasNext = page.hasNext,
            currentPage = page.currentPage
        )
    }
    companion object {
        fun <T> from(allItems: List<T>, requestPage: Int, pageSize: Int):Page<T> {
            val from = requestPage * pageSize
            val until = min(from + pageSize, allItems.size)
            return Page(
                items = allItems.subList(from, until),
                hasPrevious = requestPage > 0,
                hasNext = until < allItems.size,
                requestPage
            )
        }
    }
}
