package woowacourse.shopping.ui.pagination

import woowacourse.shopping.model.ShoppingItem

class ProductPageStateHolder(
    shoppingItems: List<ShoppingItem>,
    initialPage: Int = 0,
) : PageStateHolder<ShoppingItem>(shoppingItems) {
    init {
        restoreCurrentPage(initialPage)
    }

    override val pageItemSize: Int = 20

    override fun getPageRange(): IntRange = initialPage..getExclusiveEndPage()

    fun nextPage() {
        updateCurrentPage(currentPage + 1)
    }

    fun canMoveToNextPage(): Boolean = isInPageRange(currentPage + 1)

    fun restoreCurrentPage(page: Int) {
        updateCurrentPage(page)
    }
}
