package woowacourse.shopping.view.customview

interface CounterViewEventListener {
    fun updateCount(counterView: CounterView, count: Int): Int?
}
