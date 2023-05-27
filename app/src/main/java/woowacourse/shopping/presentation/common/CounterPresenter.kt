package woowacourse.shopping.presentation.common

import woowacourse.shopping.Counter

class CounterPresenter constructor(
    private val view: CounterContract.View,
    private val counterListener: CounterListener,
    minimumCount: Int = INIT_NUMBER,
    initCount: Int = INIT_NUMBER,
) : CounterContract.Presenter {

    private var counter: Counter = Counter(value = initCount, minimumCount = minimumCount)
    override fun updateCount(count: Int) {
        counter = counter.set(count)
        view.setCountText(counter.value)
    }

    override fun plusCount() {
        counter += INCREMENT_VALUE
        counterListener.onPlus(counter.value)
        view.setCountText(counter.value)
    }

    override fun minusCount() {
        counter -= DECREMENT_VALUE
        counterListener.onMinus(counter.value)
        view.setCountText(counter.value)
    }

    override fun checkCounterVisibility() {
        if (counter.value == NOT_VISIBLE_CONDITION) {
            view.setCounterVisibility(false)
        } else {
            view.setCounterVisibility(true)
        }
    }

    companion object {
        private const val NOT_VISIBLE_CONDITION = 0
        private const val INCREMENT_VALUE = 1
        private const val DECREMENT_VALUE = 1
        private const val INIT_NUMBER = 1
    }
}
