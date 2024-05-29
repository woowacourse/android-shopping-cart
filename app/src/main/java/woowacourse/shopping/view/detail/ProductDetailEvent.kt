package woowacourse.shopping.view.detail

sealed interface ProductDetailEvent {
    sealed interface ErrorEvent : ProductDetailEvent {
        data object NotKnownError : ErrorEvent
    }

    sealed interface SuccessEvent : ProductDetailEvent

    sealed interface AddShoppingCart : ProductDetailEvent {
        data class Success(val productId: Long, val count: Int) : AddShoppingCart, SuccessEvent

        data object Fail : AddShoppingCart, ErrorEvent
    }

    sealed interface LoadProductItem : ProductDetailEvent {
        data object Success : LoadProductItem

        data object Fail : LoadProductItem, ErrorEvent
    }

    sealed interface UpdateRecentlyProductItem : ProductDetailEvent {
        data object Success : UpdateRecentlyProductItem, SuccessEvent

        data object Fail : UpdateRecentlyProductItem, ErrorEvent
    }
}
