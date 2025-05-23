package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.CartItem

interface ShoppingCartEventHandler {
    fun onRemoveCartItem(cartItem: CartItem)

    fun onGoToPreviousPage()

    fun onGoToNextPage()
}
