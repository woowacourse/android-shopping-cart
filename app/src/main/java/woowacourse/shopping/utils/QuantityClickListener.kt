package woowacourse.shopping.utils

import woowacourse.shopping.domain.product.CartItem

interface QuantityClickListener {
    fun onClickIncrease(cartItem: CartItem)

    fun onClickDecrease(cartItem: CartItem)
}
