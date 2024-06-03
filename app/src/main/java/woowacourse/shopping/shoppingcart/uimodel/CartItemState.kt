package woowacourse.shopping.shoppingcart.uimodel

sealed interface CartItemState {
    val currentCartItems: List<CartItemUiModel>

    data class Init(override val currentCartItems: List<CartItemUiModel>) : CartItemState

    data class Change(override val currentCartItems: List<CartItemUiModel>) : CartItemState

    data class Loading(override val currentCartItems: List<CartItemUiModel>) : CartItemState

    data class Error(override val currentCartItems: List<CartItemUiModel>) : CartItemState
}
