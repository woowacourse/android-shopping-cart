package woowacourse.shopping.ui.detail

sealed interface DetailEvent {
    data object NavigateToCart : DetailEvent

    data object ShowAddCartFailureMessage : DetailEvent
}
