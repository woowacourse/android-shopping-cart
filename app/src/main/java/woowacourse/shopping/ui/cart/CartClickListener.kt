package woowacourse.shopping.ui.cart

import woowacourse.shopping.domain.cart.CartProduct

fun interface CartClickListener {
    fun onClick(cartProduct: CartProduct)
}
