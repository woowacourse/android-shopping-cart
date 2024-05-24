package woowacourse.shopping.presentation.cart

sealed interface CartErrorEvent {
    data object DeleteCartProduct : CartErrorEvent

    data object UpdateCartProducts : CartErrorEvent

    data object CanLoadMoreCartProducts : CartErrorEvent

    data object LoadCartProducts : CartErrorEvent

    data object DecreaseCartCountLimit : CartErrorEvent
}
