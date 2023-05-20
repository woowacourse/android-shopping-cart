package woowacourse.shopping.presentation.common

import woowacourse.shopping.Counter
import woowacourse.shopping.util.SafeLiveData
import woowacourse.shopping.util.SafeMutableLiveData

class CounterPresenter constructor(
    private val view: CounterContract.View,
    initCount: Int = INIT_NUMBER,
) : CounterContract.Presenter {

    private val _counter: SafeMutableLiveData<Counter> =
        SafeMutableLiveData(Counter(initCount))
    override val counter: SafeLiveData<Counter> get() = _counter
    override fun updateCount(count: Int) {
        _counter.value = _counter.value.set(count)
    }

    override fun plusCount() {
        _counter.value = _counter.value.plus(INCREMENT_VALUE)
    }

    override fun minusCount() {
        _counter.value = _counter.value.minus(DECREMENT_VALUE)
    }

    override fun checkCounterVisibility() {
        if (counter.value.value == VISIBILITY_CONDITION) {
            view.setCounterVisibility(false)
        } else {
            view.setCounterVisibility(true)
        }
    }

    companion object {
        private const val VISIBILITY_CONDITION = 0
        private const val INCREMENT_VALUE = 1
        private const val DECREMENT_VALUE = 1
        private const val INIT_NUMBER = 1
    }
}
