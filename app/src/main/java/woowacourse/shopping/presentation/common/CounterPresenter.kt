package woowacourse.shopping.presentation.common

import woowacourse.shopping.Counter

class CounterPresenter(private val view: CounterContract.View) : CounterContract.Presenter {
    override var counter: Counter = Counter()

    init {
        view.setCounterText(counter.value)
    }

    override fun updateCount(count: Int) {
        counter = counter.set(count)
        view.setCounterText(counter.value)
    }

    override fun plusCount() {
        counter += INCREMENT_VALUE
        view.setCounterText(counter.value)
    }

    override fun minusCount() {
        counter -= DECREMENT_VALUE
        view.setCounterText(counter.value)
    }

    override fun checkCounterVisibility() {
        if (counter.value == 0) {
            view.setCounterVisibility(false)
        } else {
            view.setCounterVisibility(true)
        }
    }

    companion object {
        private const val INCREMENT_VALUE = 1
        private const val DECREMENT_VALUE = 1
    }
}
