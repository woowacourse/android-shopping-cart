package woowacourse.shopping.presentation.ui.cart

import woowacourse.shopping.presentation.ui.counter.CartCounterHandler

interface CartItemCountHandler : CartCounterHandler {
    override fun increaseCount(itemId: Long)

    override fun decreaseCount(itemId: Long)
}
