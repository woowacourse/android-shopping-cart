package woowacourse.shopping.view.cartcounter

import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.Product

interface OnClickCartItemCounter {
    fun clickIncrease(product: Product, cartItemCounter: CartItemCounter)
    fun clickDecrease(product: Product, cartItemCounter: CartItemCounter)
}
