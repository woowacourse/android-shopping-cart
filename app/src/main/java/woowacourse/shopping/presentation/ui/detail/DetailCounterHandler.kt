package woowacourse.shopping.presentation.ui.detail

import woowacourse.shopping.presentation.ui.counter.DefaultCounterHandler

interface DetailCounterHandler : DefaultCounterHandler {
    override fun increaseCount()

    override fun decreaseCount()
}
