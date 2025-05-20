package woowacourse.shopping.view.cart.vm

data class CartUiState(
    val carts: List<CartState> = emptyList(),
    val pageState: PageState = PageState(),
)
