package woowacourse.shopping.ui.cart.event

sealed class ShoppingCartError {
    data object LoadCart : ShoppingCartError()

    data object FinalPage : ShoppingCartError()

    data object DeleteItem : ShoppingCartError()

    data object UpdateItemQuantity : ShoppingCartError()
}
