package woowacourse.shopping.productdetail.uimodel

import woowacourse.shopping.util.UiState

sealed interface RecentProductState : UiState {
    data class Show(
        val name: String,
        val id: Long,
    ) : RecentProductState,
        UiState.Complete

    data object Same :
        RecentProductState,
        UiState.Complete

    data object NoRecentProduct :
        RecentProductState,
        UiState.Complete
}
