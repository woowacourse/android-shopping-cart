package woowacourse.shopping.ui

import woowacourse.shopping.domain.product.CartItem

interface QuantityClickListener {
    fun onIncreaseClick(cartItem: CartItem)

    fun onDecreaseClick(cartItem: CartItem)
}
