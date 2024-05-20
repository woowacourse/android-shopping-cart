package woowacourse.shopping.view.cart

interface ShoppingCartActionHandler {
    fun onRemoveCartItemButtonClicked(cartItemId: Long)

    fun onPreviousPageButtonClicked()

    fun onNextPageButtonClicked()

    fun onBackButtonClicked()

    fun onCartItemClicked(productId: Long)
}
