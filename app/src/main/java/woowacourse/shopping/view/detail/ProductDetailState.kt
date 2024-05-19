package woowacourse.shopping.view.detail

sealed interface ProductDetailState {
    sealed interface ErrorState : ProductDetailState{
        data object NotKnownError : ErrorState
    }

    sealed interface AddShoppingCart : ProductDetailState {
        data object Success : AddShoppingCart

        data object Fail : AddShoppingCart, ErrorState
    }

    sealed interface LoadProductItem : ProductDetailState {
        data object Success : LoadProductItem

        data object Fail : LoadProductItem, ErrorState
    }
}
