package woowacourse.shopping.presentation.home

sealed interface HomeUiEvent {
    data object NavigateToCart : HomeUiEvent
    data class NavigateToDetail(val productId: Long, val lastlyViewedId: Long?) : HomeUiEvent
}
