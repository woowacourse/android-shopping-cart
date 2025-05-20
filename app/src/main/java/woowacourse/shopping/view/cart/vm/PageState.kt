package woowacourse.shopping.view.cart.vm

data class PageState(
    val page: Int = 0,
    val pageVisibility: Boolean = false,
    val previousPageEnabled: Boolean = false,
    val nextPageEnabled: Boolean = false,
)
