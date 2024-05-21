package woowacourse.shopping.view.cartcounter

import woowacourse.shopping.domain.model.CartItemCounter

interface OnClickCartItemCounter {
    fun clickIncrease(productId:Long,itemPosition: Int, cartItemCounter: CartItemCounter)
    fun clickDecrease(productId:Long,itemPosition: Int, cartItemCounter: CartItemCounter)
}
