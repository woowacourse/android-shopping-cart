package woowacourse.shopping.ui.pagination

import kotlin.math.max
import kotlin.math.min

abstract class PageStateHolder<T : Any>(
    items: List<T>,
) {
    protected abstract val pageItemSize: Int

    protected val initialPage: Int = 0
    private val pageCount: Int get() {
        if (pageItemSize == 0) return 0

        val loadedItemSize = loadedItems.size
        val totalPageCount = loadedItemSize / pageItemSize
        return if (loadedItemSize % pageItemSize == 0) {
            totalPageCount
        } else {
            totalPageCount + 1
        }
    }

    var currentPage: Int = initialPage
        private set

    private var loadedItems: List<T> = items.toList()

    protected abstract fun getPageRange(): IntRange

    fun getItems(): List<T> {
        val range = getPageRange()
        return loadedItems.subList(
            max(range.first * pageItemSize, initialPage),
            min(range.last * pageItemSize, loadedItems.size),
        )
    }

    fun updateItems(items: List<T>) {
        loadedItems = items.toList()
        syncCurrentPageWithItems()
    }

    private fun syncCurrentPageWithItems() {
        if (loadedItems.isEmpty()) {
            currentPage = initialPage
            return
        }

        val lastPage = pageCount - 1
        if (currentPage > lastPage) {
            currentPage = lastPage
        }
    }

    protected fun isInPageRange(page: Int): Boolean = page in (initialPage..<pageCount)

    protected fun updateCurrentPage(page: Int) {
        if (pageCount == 0) return
        currentPage = page.coerceIn(initialPage..<pageCount)
    }

    protected fun getExclusiveEndPage(): Int = currentPage + 1
}
