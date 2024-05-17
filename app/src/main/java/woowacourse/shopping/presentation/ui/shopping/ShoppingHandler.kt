package woowacourse.shopping.presentation.ui.shopping

interface ShoppingHandler {
    fun onProductClick(productId: Long)

    fun onLoadMoreClick()
}
