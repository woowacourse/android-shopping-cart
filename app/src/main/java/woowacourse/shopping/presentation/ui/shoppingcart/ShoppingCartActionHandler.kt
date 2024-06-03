package woowacourse.shopping.presentation.ui.shoppingcart

interface ShoppingCartActionHandler {
    fun onClickClose(orderId: Int)

    fun onClickPlusOrderButton(orderId: Int)

    fun onClickMinusOrderButton(orderId: Int)
}
