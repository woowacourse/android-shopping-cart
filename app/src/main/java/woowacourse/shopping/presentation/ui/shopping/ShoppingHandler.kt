package woowacourse.shopping.presentation.ui.shopping

interface ShoppingHandler {
    fun onClick(productId: Long)

    fun loadMore()
}
