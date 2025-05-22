package woowacourse.shopping.view.cart

sealed interface CartUiEvent {
    data class ShowCannotIncrease(val quantity: Int) : CartUiEvent
}
