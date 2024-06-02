package woowacourse.shopping.shoppingcart.uimodel

sealed interface CountChangeEvent {
    data class DeleteCartItem(
        val result: Long,
    ) : CountChangeEvent

    data class AddNextPageOfItem(
        val result: CartItemUiModel,
    ) : CountChangeEvent

    data class ChangeItemCount(
        val result: CartItemUiModel,
    ) : CountChangeEvent
}
