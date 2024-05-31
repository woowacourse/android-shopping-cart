package woowacourse.shopping.presentation.ui.cart

interface CartActionHandler {
    fun onProductClick(productId: Long)

    fun onDeleteItemClick(productId: Long)

    fun onPlusButtonClicked(productId: Long)

    fun onMinusButtonClicked(productId: Long)
}
