package woowacourse.shopping.view.main.state

sealed interface IncreaseState {
    data class CanIncrease(val value: ProductState) : IncreaseState

    data class CannotIncrease(val quantity: Int) : IncreaseState
}
