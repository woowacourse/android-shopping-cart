package woowacourse.shopping.productdetail

import woowacourse.shopping.util.UiState

sealed class CountState : UiState {
    abstract val countResult: CountResultUiModel

    data class ShowCount(
        override val countResult: CountResultUiModel,
    ) : CountState(),
        UiState.Complete

    data class ChangeItemCount(
        override val countResult: CountResultUiModel,
    ) : CountState(),
        UiState.Complete

    data class MinusFail(
        override val countResult: CountResultUiModel,
    ) : CountState(),
        UiState.Fail

    data class PlusFail(
        override val countResult: CountResultUiModel,
    ) :
        CountState(),
            UiState.Fail
}
