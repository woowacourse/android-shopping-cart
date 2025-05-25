package woowacourse.shopping.view.detail

sealed interface DetailUiEvent {
    data object MoveToCart : DetailUiEvent

    data class MoveToLastSeenProduct(val productId: Long) : DetailUiEvent

    data object ShowCannotDecrease : DetailUiEvent

    data class ShowCannotIncrease(val quantity: Int) : DetailUiEvent
}
