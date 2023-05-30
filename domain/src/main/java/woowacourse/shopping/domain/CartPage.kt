package woowacourse.shopping.domain

class CartPage(val currentPage: Int = 0) {

    fun decrementPage(): CartPage {
        return CartPage(currentPage - 1)
    }

    fun incrementPage(): CartPage {
        return CartPage(currentPage + 1)
    }

    fun getStartItemPosition(): Int =
        (currentPage) * DISPLAY_CART_COUNT_CONDITION

    companion object {
        private const val DISPLAY_CART_COUNT_CONDITION = 3
    }
}
