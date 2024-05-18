package woowacourse.shopping.view.detail

sealed interface ProductDetailState {
    data object Init : ProductDetailState
    data object Error: ProductDetailState

    sealed interface AddShoppingCart : ProductDetailState {
        data object Success : AddShoppingCart

        data object Fail : AddShoppingCart
    }

    sealed interface LoadProductItem : ProductDetailState {
        data object Success : LoadProductItem

        data object Fail : LoadProductItem
    }
}
