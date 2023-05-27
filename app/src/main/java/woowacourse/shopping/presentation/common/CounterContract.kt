package woowacourse.shopping.presentation.common

interface CounterContract {
    interface Presenter {
        fun updateCount(count: Int)
        fun checkCounterVisibility()
        fun plusCount()
        fun minusCount()
    }

    interface View {
        fun setCountText(count: Int)
        fun setCounterVisibility(visible: Boolean)
    }
}
