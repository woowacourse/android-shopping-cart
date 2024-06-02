package woowacourse.shopping.shoppingcart.uimodel

import woowacourse.shopping.util.UiState

sealed class LoadCartItemState : UiState {
    abstract val currentCartItems: List<CartItemUiModel>

    data class InitView(
        override val currentCartItems: List<CartItemUiModel>,
    ) : LoadCartItemState(), UiState.Complete

    data class DeleteCartItem(
        override val result: Long,
        override val currentCartItems: List<CartItemUiModel>,
    ) :
        LoadCartItemState(), UiState.CompleteWithResult<Long>

    data class AddNextPageOfItem(
        override val result: CartItemUiModel,
        override val currentCartItems: List<CartItemUiModel>,
    ) : LoadCartItemState(), UiState.CompleteWithResult<CartItemUiModel>

    data class ChangeItemCount(
        override val currentCartItems: List<CartItemUiModel>,
        override val result: CartItemUiModel,
    ) : LoadCartItemState(), UiState.CompleteWithResult<CartItemUiModel>

    data class MinusFail(override val currentCartItems: List<CartItemUiModel>) :
        LoadCartItemState(),
        UiState.Fail

    data class PlusFail(override val currentCartItems: List<CartItemUiModel>) :
        LoadCartItemState(),
        UiState.Fail
}
