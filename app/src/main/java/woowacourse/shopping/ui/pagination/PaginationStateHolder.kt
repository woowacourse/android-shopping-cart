package woowacourse.shopping.ui.pagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlin.math.max
import kotlin.math.min

abstract class PaginationStateHolder<T : Any>(
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

    private var loadedItems: MutableList<T> = items.toMutableList()

    protected abstract fun getPageRange(): IntRange

    fun getItems(): List<T> {
        val range = getPageRange()
        return loadedItems.subList(
            max(range.first * pageSize, initialPage),
            min(range.last * pageSize, loadedItems.size),
        )
    }

    fun updateCurrentPage(page: Int) {
        if (pageCount == 0) return
        currentPage = page.coerceIn(initialPage..<pageCount)
    }
}
