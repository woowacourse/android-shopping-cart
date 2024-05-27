package woowacourse.shopping.presentation.ui.shopping

sealed interface NavigateUiState {
    data class ToDetail(val productId: Long) : NavigateUiState

    data object ToCart : NavigateUiState
}
