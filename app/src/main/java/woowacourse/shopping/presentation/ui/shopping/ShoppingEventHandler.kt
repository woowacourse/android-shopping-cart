package woowacourse.shopping.presentation.ui.shopping

interface ShoppingEventHandler {
    fun onProductClick(productId: Long)

    fun updateCartCount()

    fun onLoadMoreButtonClick()

    fun onShoppingCartButtonClick()
}
