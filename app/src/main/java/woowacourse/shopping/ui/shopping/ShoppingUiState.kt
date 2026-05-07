package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.Products

data class ShoppingUiState(
    val products: List<ShoppingProductUiState> = emptyList(),
    val hasNext: Boolean = false,
    val isLoading: Boolean = false,
)
