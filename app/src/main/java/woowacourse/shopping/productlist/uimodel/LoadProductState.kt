package woowacourse.shopping.productlist.uimodel

import woowacourse.shopping.util.UiState

sealed class LoadProductState() : UiState {
    abstract val currentProducts: List<ProductUiModel>

    data class ShowProducts(
        override val result: List<ProductUiModel>,
        override val currentProducts: List<ProductUiModel>,
    ) : LoadProductState(),
        UiState.CompleteWithResult<List<ProductUiModel>>

    data class ChangeItemCount(
        override val result: List<ProductUiModel>,
        override val currentProducts: List<ProductUiModel>,
    ) : LoadProductState(),
        UiState.CompleteWithResult<List<ProductUiModel>>

    data class PlusFail(override val currentProducts: List<ProductUiModel>) :
        LoadProductState(),
        UiState.Fail
}
