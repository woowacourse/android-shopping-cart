package woowacourse.shopping.productlist.uimodel

sealed interface ProductUiState {
    val currentProducts: List<ProductUiModel>

    data class Loading(override val currentProducts: List<ProductUiModel>) : ProductUiState

    data class Init(override val currentProducts: List<ProductUiModel>) : ProductUiState

    data class Change(override val currentProducts: List<ProductUiModel>) : ProductUiState

    data class Error(override val currentProducts: List<ProductUiModel>) : ProductUiState
}
