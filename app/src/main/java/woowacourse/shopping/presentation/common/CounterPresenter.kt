package woowacourse.shopping.presentation.common

import woowacourse.shopping.Counter

class CounterPresenter(private val view: CounterContract.View) : CounterContract.Presenter {
    private var counter: Counter = Counter()

    init {
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

    companion object {
        private const val INCREMENT_VALUE = 1
        private const val DECREMENT_VALUE = 1
    }
}
