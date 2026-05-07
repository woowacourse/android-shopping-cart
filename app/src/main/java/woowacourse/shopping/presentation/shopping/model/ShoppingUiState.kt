package woowacourse.shopping.presentation.shopping.model

import woowacourse.shopping.presentation.common.model.ProductUiModel

data class ShoppingUiState(
    val products: List<ProductUiModel> = emptyList(),
    val canLoadMore: Boolean = true,
    val isLoading: Boolean = false,
    val offset: Int = 0,
)
