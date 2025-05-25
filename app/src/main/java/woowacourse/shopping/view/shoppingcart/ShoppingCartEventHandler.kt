package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.CartProduct

interface ShoppingCartEventHandler {
    fun onGoToPreviousPage()

    fun onGoToNextPage()

    fun onIncreaseQuantity(
        position: Int,
        cartProduct: CartProduct,
    )

    fun onDecreaseQuantity(
        position: Int,
        cartProduct: CartProduct,
    )

    fun onRemoveCartItem(cartProduct: CartProduct)
}
