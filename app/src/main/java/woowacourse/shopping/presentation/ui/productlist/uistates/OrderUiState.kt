package woowacourse.shopping.presentation.ui.productlist.uistates

import woowacourse.shopping.domain.model.Order

sealed class OrderUiState {
    data class Success(
        val orders: List<Order>,
        val orderSum: Int,
    ) : OrderUiState()

    data object Loading : OrderUiState()

    data object Failure : OrderUiState()
}
