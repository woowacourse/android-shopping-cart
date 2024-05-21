package woowacourse.shopping.view.cartcounter

import woowacourse.shopping.domain.model.CartItemCounter
import woowacourse.shopping.domain.model.Product

interface OnClickCartItemCounter {
    fun clickIncrease(product: Product, itemPosition: Int, cartItemCounter: CartItemCounter)
    fun clickDecrease(product: Product, itemPosition: Int, cartItemCounter: CartItemCounter)
}
