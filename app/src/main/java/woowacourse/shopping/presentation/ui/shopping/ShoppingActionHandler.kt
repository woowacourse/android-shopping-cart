package woowacourse.shopping.presentation.ui.shopping

interface ShoppingActionHandler {
    fun onProductClick(productId: Long)

    fun onLoadMoreButtonClick()
}
