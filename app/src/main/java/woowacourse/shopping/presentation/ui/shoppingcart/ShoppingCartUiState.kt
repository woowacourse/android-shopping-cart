package woowacourse.shopping.presentation.ui.shoppingcart

import woowacourse.shopping.domain.model.Order

data class ShoppingCartUiState(
    val pagingOrder: PagingOrder = PagingOrder(),
)

data class PagingOrder(
    val orders: List<Order> = emptyList(),
    val currentPage: Int = 0,
    val last: Boolean = true,
)
