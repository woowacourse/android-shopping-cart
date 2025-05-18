package woowacourse.shopping.view.detail

sealed interface DetailScreenEvent {
    data object MoveToCart : DetailScreenEvent
}
