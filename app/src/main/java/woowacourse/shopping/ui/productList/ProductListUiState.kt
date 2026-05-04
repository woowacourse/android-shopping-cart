package woowacourse.shopping.ui.productList

import woowacourse.shopping.domain.product.Product

sealed interface ProductListUiState {
    data object Loading : ProductListUiState

    data class Success(
        val products: List<Product>,
        val canLoadMore: Boolean,
    ) : ProductListUiState

    data class Error(
        val throwable: Throwable,
    ) : ProductListUiState
}
