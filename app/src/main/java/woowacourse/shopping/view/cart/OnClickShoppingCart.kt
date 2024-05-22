package woowacourse.shopping.view.cart

import woowacourse.shopping.domain.model.CartItem

interface OnClickShoppingCart {
    fun clickBack()

    fun clickCartItem(productId: Long)

    fun clickRemoveCartItem(cartItem: CartItem)

    fun clickPrevPage()

    fun clickNextPage()
}
