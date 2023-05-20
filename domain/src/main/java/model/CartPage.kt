package model

class CartPage(
    override val cart: Cart,
) : CartPagination {

    override var currentPage = Page()
    override val endPage: Page
        get() = getPageOfEnd(cart.products.size)

    override val isReachedEndPage: Boolean
        get() = currentPage == endPage

    override val showingProducts: List<CartProduct>
        get() = getCartProductOnPage()

    override val totalPrice: Int
        get() = showingProducts.filter { it.selected }
            .sumOf { it.price.value }

    private fun getCartProductOnPage(): List<CartProduct> {
        val startIndex = currentPage.value * ITEM_COUNT_ON_EACH_PAGE
        val endIndex = (startIndex + ITEM_COUNT_ON_EACH_PAGE).coerceAtMost(cart.products.size)

        if (startIndex >= cart.products.size) {
            return emptyList()
        }
        return cart.products.subList(startIndex, endIndex)
    }

    private fun getPageOfEnd(totalProductsSize: Int): Page {
        if (totalProductsSize == 0) {
            return Page()
        }
        return Page((totalProductsSize - 1) / ITEM_COUNT_ON_EACH_PAGE)
    }

    override fun moveToNextPage(
        callBack: (cartPage: CartPagination) -> Unit,
        onReachedEndPage: () -> Unit,
    ) {
        if (isReachedEndPage) {
            onReachedEndPage()
        }
        if (currentPage < endPage) {
            currentPage = currentPage.next()
            callBack(this)
        }
    }

    override fun moveToPreviousPage(callBack: (cartPage: CartPagination) -> Unit) {
        currentPage = currentPage.prev()
        callBack(this)
    }

    companion object {
        const val ITEM_COUNT_ON_EACH_PAGE = 3
    }
}
