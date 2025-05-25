package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.CartProduct

interface ShoppingCartEventHandler {
    fun onGoToPreviousPage()

    fun onGoToNextPage()

    fun onIncreaseQuantity(
        position: Int,
        product: CartProduct,
    )

    fun onDecreaseQuantity(
        position: Int,
        product: CartProduct,
    )

    fun onRemoveProduct(product: CartProduct)
}
