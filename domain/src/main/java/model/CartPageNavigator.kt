package model

interface CartPageNavigator {

    val cart: Cart
    var currentPage: Page
    val endPage: Page
    val isReachedEndPage: Boolean
    val showingProducts: List<CartProduct>

    fun moveToNextPage(
        onPageChanged: (cartPage: CartPageNavigator) -> Unit,
        onReachedEndPage: () -> Unit
    )

    fun moveToPreviousPage(onPageChanged: (cartPage: CartPageNavigator) -> Unit)
    val totalPrice: Int
}
