package woowacourse.shopping.ui.productDetail

import woowacourse.shopping.domain.product.Product

sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState

    data class Success(
        val product: Product,
        val selectedQuantity: Int = 1,
        val lastViewedProduct: Product? = null,
    ) : ProductDetailUiState

    data class Error(
        val throwable: Throwable,
    ) : ProductDetailUiState
}
