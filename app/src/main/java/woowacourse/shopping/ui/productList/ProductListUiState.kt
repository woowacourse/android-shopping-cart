package woowacourse.shopping.ui.productList

import woowacourse.shopping.domain.product.Product

sealed interface ProductListUiState {
    data object Loading : ProductListUiState

    data class Success(
        val products: List<Product>,
        val currentProductCount: Int,
        val totalProductCount: Int,
    ) : ProductListUiState {
        val canLoadMore: Boolean
            get() = currentProductCount < totalProductCount

        val visibleProducts: List<Product>
            get() = products.take(currentProductCount)
    }

    data class Error(
        val throwable: Throwable,
    ) : ProductListUiState
}
