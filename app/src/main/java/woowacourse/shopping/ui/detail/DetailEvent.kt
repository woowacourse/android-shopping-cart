package woowacourse.shopping.ui.detail

sealed interface DetailEvent {
    data object NavigateToCart : DetailEvent

    data object NavigateBack : DetailEvent

    data object ShowProductNotFoundMessage : DetailEvent

    data object ShowProductLoadFailureMessage : DetailEvent

    data object ShowAddCartFailureMessage : DetailEvent
}
