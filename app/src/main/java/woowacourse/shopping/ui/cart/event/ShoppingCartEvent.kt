package woowacourse.shopping.ui.cart.event

sealed class ShoppingCartEvent {
    data class DeleteItem(val cartItemId: Long) : ShoppingCartEvent()

    data object PopBackStack : ShoppingCartEvent()
}
