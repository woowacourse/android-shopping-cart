package woowacourse.shopping.view.shoppingcart

import woowacourse.shopping.domain.CartProduct

interface ShoppingCartEventHandler {
    fun onGoToPreviousPage()

    fun onGoToNextPage()

    fun onIncreaseQuantity(product: CartProduct)

    fun onDecreaseQuantity(product: CartProduct)

    fun onRemoveProduct(product: CartProduct)
}
