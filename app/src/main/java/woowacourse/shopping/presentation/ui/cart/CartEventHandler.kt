package woowacourse.shopping.presentation.ui.cart

interface CartEventHandler {
    fun navigateToShopping()

    fun navigateToDetail(itemId: Long)

    fun deleteCartItem(itemId: Long)
}
