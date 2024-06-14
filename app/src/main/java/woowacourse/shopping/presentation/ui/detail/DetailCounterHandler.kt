package woowacourse.shopping.presentation.ui.detail

import woowacourse.shopping.presentation.ui.counter.CounterHandler

interface DetailCounterHandler : CounterHandler {
    override fun increaseCount(productId: Long)

    override fun decreaseCount(productId: Long)
}
