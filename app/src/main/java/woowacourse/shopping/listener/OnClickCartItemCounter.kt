package woowacourse.shopping.listener

import woowacourse.shopping.model.CartItem

interface OnClickCartItemCounter {
    fun increaseQuantity(cartItem: CartItem)
    fun decreaseQuantity(cartItem: CartItem)
}
