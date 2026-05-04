package woowacourse.shopping.ui.pagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.max
import kotlin.math.min

abstract class PageStateHolder<T : Any>(
    items: List<T>,
) {
    protected abstract val pageSize: Int

    protected val initialPage: Int = 0
    private val pageCount: Int get() {
        if (pageSize == 0) return 0

        val loadedItemSize = loadedItems.size
        val totalPageCount = loadedItemSize / pageSize
        return if (loadedItemSize % pageSize == 0) {
            totalPageCount
        } else {
            totalPageCount + 1
        }
    }

    var currentPage: Int by mutableIntStateOf(initialPage)
        private set

    private var loadedItems: List<T> by mutableStateOf(items.toList())

    protected abstract fun getPageRange(): IntRange

    fun getItems(): List<T> {
        val range = getPageRange()
        return loadedItems.subList(
            max(range.first * pageSize, initialPage),
            min(range.last * pageSize, loadedItems.size),
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
