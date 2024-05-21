package woowacourse.shopping.view.cartcounter

import woowacourse.shopping.domain.model.CartItemCounter

interface OnClickCartItemCounter {
    fun clickIncrease(productId:Int,itemPosition: Int, cartItemCounter: CartItemCounter)
    fun clickDecrease(productId:Int,itemPosition: Int, cartItemCounter: CartItemCounter)
}
