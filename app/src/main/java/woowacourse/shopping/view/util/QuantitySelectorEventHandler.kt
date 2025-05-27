package woowacourse.shopping.view.util

import woowacourse.shopping.domain.Cart

interface QuantitySelectorEventHandler {
    fun onQuantityMinus(cart: Cart)

    fun onQuantityPlus(cart: Cart)
}
