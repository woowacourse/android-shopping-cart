package woowacourse.shopping.presentation.common

import woowacourse.shopping.Counter

interface CounterContract {
    interface Presenter {
        var counter: Counter
        fun updateCount(count: Int)
        fun plusCount()
        fun minusCount()
        fun checkCounterVisibility()
    }

    interface View {
        fun setCounterText(number: Int)
        fun setCounterVisibility(visible: Boolean)
    }
}
