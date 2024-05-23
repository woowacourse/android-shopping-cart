package woowacourse.shopping.presentation.ui.shopping

interface ShoppingEventHandler {
    fun onProductClick(productId: Long)

    fun onLoadMoreButtonClick()

    fun onShoppingCartButtonClick()
}
