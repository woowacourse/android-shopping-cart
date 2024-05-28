package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.presentation.ui.counter.CounterHandler

interface ShoppingItemCountHandler : CounterHandler {
    override fun increaseCount(productId: Long)

    override fun decreaseCount(productId: Long)
}
