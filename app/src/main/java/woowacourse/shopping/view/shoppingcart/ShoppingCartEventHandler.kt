package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.CartItem

interface ShoppingCartEventHandler {
    fun onGoToPreviousPage()

    fun onGoToNextPage()

    fun onIncreaseQuantity(
        position: Int,
        cartItem: CartItem,
    )

    fun onDecreaseQuantity(
        position: Int,
        cartItem: CartItem,
    )

    fun onRemoveCartItem(cartItem: CartItem)
}
