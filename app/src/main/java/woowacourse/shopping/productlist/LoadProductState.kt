package woowacourse.shopping.productlist

import woowacourse.shopping.util.UiState

sealed class LoadProductState() : UiState {
    abstract val currentProducts: ProductUiModels

    data object BeforeInit : UiState.Loading

    data class ShowProducts(
        override val result: ProductUiModels,
        override val currentProducts: ProductUiModels,
    ) : LoadProductState(),
        UiState.Complete<ProductUiModels>

    data class ChangeItemCount(
        override val result: ProductUiModel,
        override val currentProducts: ProductUiModels,
    ) : LoadProductState(),
        UiState.Complete<ProductUiModel>

    data class DeleteProductFromCart(
        override val currentProducts: ProductUiModels,
        override val result: ProductUiModel,
    ) : LoadProductState(),
        UiState.Complete<ProductUiModel>

    data class PlusFail(override val currentProducts: ProductUiModels) :
        LoadProductState(),
        UiState.Fail

    data class Error(override val throwable: Throwable?) : UiState.Error
}
