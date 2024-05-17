package woowacourse.shopping.view.products

sealed interface ProductListState {
    data object Init : ProductListState

    sealed interface LoadProductList : ProductListState {
        data object Success : LoadProductList

        data object Fail : LoadProductList
    }
}
