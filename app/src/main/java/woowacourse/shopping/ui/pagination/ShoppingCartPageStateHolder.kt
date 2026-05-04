package woowacourse.shopping.ui.pagination

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import woowacourse.shopping.model.ShoppingCartItem
import kotlin.math.min

class ShoppingCartPageStateHolder(
    shoppingCartItems: List<ShoppingCartItem>,
) {
    private val pageSize: Int = 5
    private val allItems: List<ShoppingCartItem> = shoppingCartItems

    var currentPage: Int by mutableIntStateOf(0)
        private set

    private val pageCount: Int
        get() {
            if (allItems.isEmpty()) return 0
            val totalPageCount = allItems.size / pageSize
            return if (allItems.size % pageSize == 0) {
                totalPageCount
            } else {
                totalPageCount + 1
            }
        }

    fun getItems(): List<ShoppingCartItem> {
        if (allItems.isEmpty()) return emptyList()
        val startItemIndex = currentPage * pageSize
        val endItemIndex = min(startItemIndex + pageSize, allItems.size)
        return allItems.subList(startItemIndex, endItemIndex)
    }

    fun beforePage() {
        if (canMoveToPreviousPage()) {
            currentPage--
        }
    }

    fun nextPage() {
        if (canMoveToNextPage()) {
            currentPage++
        }
    }

    fun canMoveToPreviousPage(): Boolean = currentPage > 0

    fun canMoveToNextPage(): Boolean = currentPage + 1 < pageCount
}
