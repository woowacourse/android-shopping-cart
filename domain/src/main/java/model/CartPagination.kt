package model

interface CartPagination {

    val cart: Cart
    var currentPage: Page
    val endPage: Page
    val isReachedEndPage: Boolean
    val showingProducts: List<CartProduct>

    fun moveToNextPage(
        callBack: (cartPage: CartPagination) -> Unit,
        onReachedEndPage: (cart: Cart) -> Unit
    )

    fun moveToPreviousPage(callBack: (cartPage: CartPagination) -> Unit)
    val totalPrice: Int
}
