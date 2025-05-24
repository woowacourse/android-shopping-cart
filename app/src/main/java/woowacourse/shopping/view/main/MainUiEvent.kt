package woowacourse.shopping.view.main

sealed interface MainUiEvent {
    data class ShowCannotIncrease(val quantity: Int) : MainUiEvent
}
