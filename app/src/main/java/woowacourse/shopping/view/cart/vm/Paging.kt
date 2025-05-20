package woowacourse.shopping.view.cart.vm

import woowacourse.shopping.data.CartStorage
import woowacourse.shopping.domain.Product

class Paging(
    private val initialPage: Int = INITIAL_PAGE_NO,
    private val pageSize: Int = PAGE_SIZE,
) {
    private var currentPage: Int = initialPage
    private var hasEverShownNextPage: Boolean = false

    fun getPageNumber(): Int = currentPage

    fun moveToNextPage() {
        currentPage++
    }

    fun moveToPreviousPage() {
        if (currentPage > initialPage) {
            currentPage--
        }
    }

    fun resetToLastPageIfEmpty(currentProducts: List<Product>?): Boolean {
        if (currentProducts.isNullOrEmpty() && currentPage > initialPage) {
            currentPage--
            return true
        }
        return false
    }

    fun createPageState(cartStorage: CartStorage): PageState {
        val hasNext = !cartStorage.notHasNextPage(currentPage, pageSize)
        if (hasNext) hasEverShownNextPage = true

        return PageState(
            previousPageEnabled = currentPage > initialPage,
            nextPageEnabled = hasNext,
            pageVisibility = hasEverShownNextPage,
        )
    }

    companion object {
        const val INITIAL_PAGE_NO = 1
        const val PAGE_SIZE = 5
    }
}
