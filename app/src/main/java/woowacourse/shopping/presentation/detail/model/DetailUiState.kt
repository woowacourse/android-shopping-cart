package woowacourse.shopping.presentation.detail.model

import woowacourse.shopping.presentation.common.model.ProductUiModel

sealed interface DetailUiState {
    object Loading : DetailUiState

    data class Success(
        val product: ProductUiModel,
        val quantity: Int,
        val lastSeenProduct: ProductUiModel? = null,
    ) : DetailUiState {
        val price: Long get() = product.price * quantity
        val showLastSeenProductCard: Boolean get() = lastSeenProduct?.let { it.id != product.id } ?: false
    }

    data class Error(
        val message: String,
    ) : DetailUiState
}
