package woowacourse.shopping.presentation.ui.cart

interface CartActionHandler {
    fun onProductClick(productId: Long)

    fun onDeleteItemClick(cartItemId: Long)
}
