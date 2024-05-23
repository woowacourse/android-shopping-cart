package woowacourse.shopping.shoppingcart

import woowacourse.shopping.util.UiState

sealed class LoadCartItemState : UiState {
    abstract val currentCartItems: CartItemUiModels

    data class InitView(
        override val currentCartItems: CartItemUiModels,
    ) : LoadCartItemState(), UiState.Complete

    data class DeleteCartItem(
        override val result: Long,
        override val currentCartItems: CartItemUiModels,
    ) :
        LoadCartItemState(), UiState.CompleteWithResult<Long>

    data class AddNextPageOfItem(
        override val result: CartItemUiModel,
        override val currentCartItems: CartItemUiModels,
    ) : LoadCartItemState(), UiState.CompleteWithResult<CartItemUiModel>

    data class ChangeItemCount(
        override val currentCartItems: CartItemUiModels,
        override val result: CartItemUiModel,
    ) : LoadCartItemState(), UiState.CompleteWithResult<CartItemUiModel>

    data class MinusFail(override val currentCartItems: CartItemUiModels) : LoadCartItemState(), UiState.Fail

    data class PlusFail(override val currentCartItems: CartItemUiModels) : LoadCartItemState(), UiState.Fail
}
