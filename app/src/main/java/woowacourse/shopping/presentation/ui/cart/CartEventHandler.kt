package woowacourse.shopping.presentation.ui.cart

interface CartEventHandler {
    fun navigateToShopping()

    fun navigateToDetail(productId: Long)

    fun deleteCartItem(itemId: Long)
}
