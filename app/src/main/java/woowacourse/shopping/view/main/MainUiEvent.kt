package woowacourse.shopping.view.main

sealed interface MainUiEvent {
    data class ShowCannotIncrease(val quantity: Int) : MainUiEvent

    data class NavigateToDetail(
        val productId: Long,
        val lastWatchedProductId: Long? = null,
    ) : MainUiEvent
}
