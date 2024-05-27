package woowacourse.shopping.presentation.ui.shopping

import woowacourse.shopping.presentation.ui.counter.DefaultCounterHandler

interface ShoppingItemCountHandler : DefaultCounterHandler {
    override fun increaseCount(productId: Long)

    override fun decreaseCount(productId: Long)
}
