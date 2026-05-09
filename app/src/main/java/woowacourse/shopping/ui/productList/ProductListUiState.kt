package woowacourse.shopping.ui.productList

import woowacourse.shopping.domain.product.Product

sealed interface ProductListUiState {
    data object Loading : ProductListUiState

    data class Success(
        val products: List<Product>,
        val recentProducts: List<Product> = emptyList(),
        val quantitiesByProductId: Map<String, Int> = emptyMap(),
        val canLoadMore: Boolean,
        val isLoadingMore: Boolean = false,
        val totalCartCount:Int = 0,
    ) : ProductListUiState

    data class Error(
        val throwable: Throwable,
    ) : ProductListUiState
}
