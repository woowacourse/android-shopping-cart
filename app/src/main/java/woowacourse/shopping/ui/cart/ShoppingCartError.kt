package woowacourse.shopping.ui.cart

sealed class ShoppingCartError {
    data object LoadCart : ShoppingCartError()

    data object FinalPage : ShoppingCartError()

    data object DeleteItem : ShoppingCartError()

    data object UpdateItemQuantity : ShoppingCartError()

}
