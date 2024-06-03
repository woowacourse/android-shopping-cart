package woowacourse.shopping.ui.cart

sealed class ShoppingCartEvent {
    data class DeleteItem(val cartItemId: Long) : ShoppingCartEvent()
}
