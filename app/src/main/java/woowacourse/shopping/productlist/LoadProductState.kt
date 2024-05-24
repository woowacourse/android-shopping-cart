package woowacourse.shopping.productlist

import woowacourse.shopping.util.UiState

sealed class LoadProductState() : UiState {
    abstract val currentProducts: ProductUiModels

    data object BeforeInit : UiState.Loading

    data class ShowProducts(
        override val result: ProductUiModels,
        override val currentProducts: ProductUiModels,
    ) : LoadProductState(),
        UiState.CompleteWithResult<ProductUiModels>

    data class ChangeItemCount(
        override val result: List<ProductUiModel>,
        override val currentProducts: ProductUiModels,
    ) : LoadProductState(),
        UiState.CompleteWithResult<List<ProductUiModel>>

    data class PlusFail(override val currentProducts: ProductUiModels) :
        LoadProductState(),
        UiState.Fail

    data class Error(override val throwable: Throwable?) : UiState.Error
}
