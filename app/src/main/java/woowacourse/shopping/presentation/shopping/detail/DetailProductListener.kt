package woowacourse.shopping.presentation.shopping.detail

import woowacourse.shopping.presentation.cart.CartItemListener

interface DetailProductListener : CartItemListener {
    fun addCartProduct()
}
