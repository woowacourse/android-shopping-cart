package woowacourse.shopping.ui.pagination

import woowacourse.shopping.model.ShoppingCartItem

class ShoppingCartPageStateHolder(
    shoppingCartItems: List<ShoppingCartItem>,
) : PageStateHolder<ShoppingCartItem>(shoppingCartItems) {
    override val pageSize: Int = 5

    override fun getPageRange(): IntRange {
        return currentPage..getExclusiveEndPage()
    }

    fun beforePage() {
        updateCurrentPage(currentPage - 1)
    }

    fun nextPage() {
        updateCurrentPage(currentPage + 1)
    }

    fun canMoveToPreviousPage(): Boolean = isInPageRange(currentPage - 1)

    fun canMoveToNextPage(): Boolean = isInPageRange(currentPage + 1)

    fun restoreCurrentPage(page: Int) {
        updateCurrentPage(page)
    }
}
