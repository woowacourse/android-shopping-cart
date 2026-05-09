package woowacourse.shopping.ui.shopping

import woowacourse.shopping.model.Product

data class ShoppingUiState(
    val isLoading: Boolean = false,
    val visibleCount: Int = 0,
    val visibleProducts: List<Product> = emptyList(),
    val hasNext: Boolean = false,
    val sizeInRepo: Int = 0
)
