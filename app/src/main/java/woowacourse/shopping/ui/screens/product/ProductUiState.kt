package woowacourse.shopping.ui.screens.product

import woowacourse.shopping.domain.Product

data class ProductUiState(
    val products: List<Product> = emptyList(),
    val hasNext: Boolean = true,
    val loadedPageCount: Int = 1,
    val isLoading: Boolean = false,
)
