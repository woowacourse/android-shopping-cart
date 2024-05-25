package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.domain.CartProduct

interface ShoppingActionHandler {
    fun onPlus(cartProduct: CartProduct)

    fun onMinus(cartProduct: CartProduct)
}
