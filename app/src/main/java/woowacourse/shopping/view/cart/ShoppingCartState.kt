package woowacourse.shopping.view.cart

sealed interface ShoppingCartState {
    data object Init : ShoppingCartState

    data object Error : ShoppingCartState

    sealed interface DeleteShoppingCart : ShoppingCartState {
        data object Success : DeleteShoppingCart

        data object Fail : DeleteShoppingCart
    }

    sealed interface LoadCartItemList : ShoppingCartState {
        data object Success : LoadCartItemList

        data object Fail : LoadCartItemList
    }
}
