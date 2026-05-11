package woowacourse.shopping.ui.screens.product

import woowacourse.shopping.domain.RecentProduct

data class ProductUiState(
    val products: List<ProductUiModel> = emptyList(),
    val recentProducts: List<RecentProduct> = emptyList(),
    val totalCartAmount: Int = 0,
    val hasNext: Boolean = true,
    val showRecentProducts: Boolean = false,
    val isLoading: Boolean = false,
    val isNetworkAvailable: Boolean = true,
)
