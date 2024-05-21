package woowacourse.shopping.view.products

sealed interface ProductListEvent {
    sealed interface ErrorEvent : ProductListEvent {
        data object NotKnownError : ErrorEvent
    }

    sealed interface LoadProductEvent : ProductListEvent {
        data object Success : LoadProductEvent

        data object Fail : LoadProductEvent, ErrorEvent
    }

    sealed interface UpdateProductEvent : ProductListEvent {
        data class Success(val productId: Long) : UpdateProductEvent

        data object Fail : UpdateProductEvent, ErrorEvent
    }

    sealed interface DeleteProductEvent : ProductListEvent {
        data class Success(val productId: Long) : DeleteProductEvent

        data object Fail : DeleteProductEvent, ErrorEvent
    }
}
