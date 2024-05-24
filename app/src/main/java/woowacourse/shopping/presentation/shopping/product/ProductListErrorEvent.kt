package woowacourse.shopping.presentation.shopping.product

sealed interface ProductListErrorEvent {
    data object IncreaseCartCount : ProductListErrorEvent

    data object LoadCartProducts : ProductListErrorEvent

    data object DecreaseCartCount : ProductListErrorEvent

    data object LoadRecentProducts : ProductListErrorEvent

    data object LoadProducts : ProductListErrorEvent
}
