package woowacourse.shopping.presentation.ui.shoppingcart

import woowacourse.shopping.domain.model.PagingOrder

data class ShoppingCartUiState(
    val currentPage: Int? = null,
    val pagingOrder: PagingOrder? = null,
)
