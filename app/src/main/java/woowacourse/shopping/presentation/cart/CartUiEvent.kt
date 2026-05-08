package woowacourse.shopping.presentation.cart

sealed interface CartUiEvent {
    data class ShowMessage(val message: String) : CartUiEvent
}
