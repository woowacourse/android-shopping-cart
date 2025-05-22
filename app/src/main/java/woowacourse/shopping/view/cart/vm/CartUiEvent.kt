package woowacourse.shopping.view.cart.vm

sealed interface CartUiEvent {
    data class ShowCannotIncrease(val quantity: Int) : CartUiEvent
}
