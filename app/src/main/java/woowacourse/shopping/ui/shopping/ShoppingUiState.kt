package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.Products

data class ShoppingUiState(
    val products: Products = Products(emptyList()),
    val hasNext: Boolean = false,
    val isLoading: Boolean = false,
)