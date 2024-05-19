package woowacourse.shopping.shoppingcart

sealed interface LoadCartItemState {
    data object InitView : LoadCartItemState

    data object AddNextPageOfItem : LoadCartItemState
}
