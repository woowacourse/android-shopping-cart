package woowacourse.shopping.view.cart

import woowacourse.shopping.domain.model.CartItem

interface ShoppingCartActionHandler {
    fun onRemoveCartItemButtonClicked(cartItem: CartItem)

    fun onPreviousPageButtonClicked()

    fun onNextPageButtonClicked()

    fun onBackButtonClicked()

    fun onCartItemClicked(productId: Long)
}
