package woowacourse.shopping.presentation.shopping.detail

sealed interface ProductDetailErrorEvent {
    data object LoadCartProduct : ProductDetailErrorEvent

    data object DecreaseCartCount : ProductDetailErrorEvent

    data object AddCartProduct : ProductDetailErrorEvent

    data object SaveRecentProduct : ProductDetailErrorEvent
}
