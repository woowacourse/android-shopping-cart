package woowacourse.shopping.view.detail

sealed interface ProductDetailState {
    data object Success : ProductDetailState

    data object Fail : ProductDetailState
}
