package woowacourse.shopping.ui.cart

interface PageableView {
    fun setStateThatCanRequestPreviousPage(canRequest: Boolean)

    fun setStateThatCanRequestNextPage(canRequest: Boolean)

    fun setPage(page: Int)
}