package woowacourse.shopping.view.cartcounter

sealed interface ChangeCartItemResultState {
    data object Success : ChangeCartItemResultState

    data object Fail : ChangeCartItemResultState
}
