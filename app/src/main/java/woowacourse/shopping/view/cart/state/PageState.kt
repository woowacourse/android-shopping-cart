package woowacourse.shopping.view.cart.state

data class PageState(
    val page: Int = 1,
    val pageVisibility: Boolean = false,
    val previousPageEnabled: Boolean = false,
    val nextPageEnabled: Boolean = false,
)
