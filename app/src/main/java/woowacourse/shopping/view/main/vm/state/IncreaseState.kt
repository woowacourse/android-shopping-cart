package woowacourse.shopping.view.main.vm.state

sealed interface IncreaseState {
    data class CanIncrease(val value: ProductState) : IncreaseState

    object CannotIncrease : IncreaseState
}
