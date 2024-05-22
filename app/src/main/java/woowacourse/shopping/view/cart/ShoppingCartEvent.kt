package woowacourse.shopping.view.cart

sealed interface ShoppingCartEvent {
    sealed interface ErrorState {
        data object NotKnownError : ErrorState
    }

    sealed interface SuccessEvent : ShoppingCartEvent

    sealed interface UpdateProductEvent : ShoppingCartEvent {
        data class Success(val productId: Long, val count: Int) : UpdateProductEvent, SuccessEvent

        data object Fail : UpdateProductEvent, ErrorState
    }

    sealed interface DeleteShoppingCart : ShoppingCartEvent {
        data object Success : DeleteShoppingCart, SuccessEvent

        data object Fail : DeleteShoppingCart, ErrorState
    }

    sealed interface LoadCartItemList : ShoppingCartEvent {
        data object Success : LoadCartItemList

        data object Fail : LoadCartItemList, ErrorState
    }
}
