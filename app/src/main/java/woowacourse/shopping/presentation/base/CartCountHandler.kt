package woowacourse.shopping.presentation.base

import woowacourse.shopping.domain.CartProduct

interface CartCountHandler {
    fun onPlus(cartProduct: CartProduct)

    fun onMinus(cartProduct: CartProduct)
}
