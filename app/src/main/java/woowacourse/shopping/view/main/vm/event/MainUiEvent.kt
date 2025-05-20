package woowacourse.shopping.view.main.vm.event

sealed interface MainUiEvent {
    data class ShowCannotIncrease(val quantity: Int) : MainUiEvent
}
