package woowacourse.shopping.shoppingcart

import woowacourse.shopping.productlist.ProductUiModel
import woowacourse.shopping.util.UiState

sealed interface LoadCartItemState : UiState {
    data class InitView(override val result: List<ProductUiModel>) :
        LoadCartItemState,
        UiState.CompleteWithResult<List<ProductUiModel>>

    data class AddNextPageOfItem(override val result: ProductUiModel) :
        LoadCartItemState,
        UiState.CompleteWithResult<ProductUiModel>
}
