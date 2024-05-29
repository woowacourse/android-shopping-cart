package woowacourse.shopping.view.products

sealed interface ProductListEvent {
    sealed interface ErrorEvent : ProductListEvent {
        data object NotKnownError : ErrorEvent
    }

    sealed interface SuccessEvent : ProductListEvent

    sealed interface LoadProductEvent : ProductListEvent {
        data object Success : LoadProductEvent

        data object Fail : LoadProductEvent, ErrorEvent
    }

    sealed interface UpdateProductEvent : ProductListEvent {
        data class Success(val productId: Long) : UpdateProductEvent, SuccessEvent

        data object Fail : UpdateProductEvent, ErrorEvent
    }

    sealed interface DeleteProductEvent : ProductListEvent {
        data class Success(val productId: Long) : DeleteProductEvent, SuccessEvent

        data object Fail : DeleteProductEvent, ErrorEvent
    }
}
