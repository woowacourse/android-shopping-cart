package woowacourse.shopping.presentation.cart

import woowacourse.shopping.presentation.base.CountHandler

interface CartAction : CountHandler {
    fun deleteProduct(cart: CartProductUi)
}
