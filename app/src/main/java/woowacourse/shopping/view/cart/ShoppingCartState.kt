package woowacourse.shopping.view.cart

sealed interface ShoppingCartState {

    sealed interface ErrorState {
        data object NotKnownError : ErrorState
    }

    sealed interface DeleteShoppingCart : ShoppingCartState {
        data object Success : DeleteShoppingCart

        data object Fail : DeleteShoppingCart, ErrorState
    }

    sealed interface LoadCartItemList : ShoppingCartState {
        data object Success : LoadCartItemList

        data object Fail : LoadCartItemList, ErrorState
    }
}
