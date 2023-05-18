package woowacourse.shopping.view.customview

interface CounterViewEventListener {
    fun addCount(counterView: CounterView, count: Int)
    fun decCount(counterView: CounterView, count: Int)
}
