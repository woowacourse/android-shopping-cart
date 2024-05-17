package woowacourse.shopping.view.cart

interface NavigationActionHandler {
    fun onBackButtonClicked()

    fun onCartItemClicked(productId: Long)
}
