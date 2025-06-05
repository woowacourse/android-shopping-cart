package woowacourse.shopping.feature

import woowacourse.shopping.domain.model.CartItem

interface QuantityChangeListener {
    fun onIncrease(cartItem: CartItem)

    fun onDecrease(cartItem: CartItem)
}
