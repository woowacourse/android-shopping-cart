package woowacourse.shopping.shoppingcart.pagination

import model.Cart
import model.CartProduct
import model.Page

interface CartPageHandler {

    val cart: Cart
    var currentPage: Page
    val endPage: Page
    val isReachedEndPage: Boolean
    val showingProducts: List<CartProduct>

    fun moveToNextPage(
        onPageChanged: (cartPage: CartPageHandler) -> Unit,
        onReachedEndPage: () -> Unit
    )

    fun moveToPreviousPage(onPageChanged: (cartPage: CartPageHandler) -> Unit)
    val totalPrice: Int
}
