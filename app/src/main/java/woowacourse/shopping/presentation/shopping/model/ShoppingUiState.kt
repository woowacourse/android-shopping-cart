package woowacourse.shopping.presentation.shopping.model

import woowacourse.shopping.presentation.common.model.ProductUiModel

data class ShoppingUiState(
    val products: List<ShoppingItemUiModel> = emptyList(),
    val canLoadMore: Boolean = true,
    val isLoading: Boolean = false,
    val offset: Int = 0,
    val totalQuantity: Int = 0,
    val recentProducts: List<ProductUiModel> = emptyList(),
    val errorMessage: String? = null,
) {
    val isShowCartQuantityIcon: Boolean get() = totalQuantity > 0
}
