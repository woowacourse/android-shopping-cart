package woowacourse.shopping.presentation.common

import woowacourse.shopping.Counter
import woowacourse.shopping.util.SafeLiveData

interface CounterContract {
    interface Presenter {
        val counter: SafeLiveData<Counter>
        fun updateCount(count: Int)
        fun plusCount()
        fun minusCount()
        fun checkCounterVisibility()
    }

    interface View {
        fun setCounterVisibility(visible: Boolean)
    }
}
