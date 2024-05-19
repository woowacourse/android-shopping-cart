package woowacourse.shopping.view.products

sealed interface ProductListState {

    sealed interface ErrorState : ProductListState {
        data object NotKnownError : ErrorState
    }

    sealed interface LoadProductList : ProductListState {
        data object Success : LoadProductList

        data object Fail : LoadProductList, ErrorState
    }
}
