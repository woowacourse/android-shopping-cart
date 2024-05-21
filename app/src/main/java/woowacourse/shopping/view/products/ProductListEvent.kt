package woowacourse.shopping.view.products

sealed interface ProductListEvent {
    sealed interface ErrorEvent : ProductListEvent {
        data object NotKnownError : ErrorEvent
    }

    sealed interface LoadProductEvent : ProductListEvent {
        data object Success : LoadProductEvent

        data object Fail : LoadProductEvent, ErrorEvent
    }
}
