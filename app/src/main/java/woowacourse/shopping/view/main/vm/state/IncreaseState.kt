package woowacourse.shopping.view.main.vm.state

import woowacourse.shopping.domain.Quantity

sealed interface IncreaseState {
    data class CanIncrease(
        val value: ProductState,
        val productStock: Quantity,
    ) : IncreaseState

    data class CannotIncrease(val quantity: Int) : IncreaseState
}
