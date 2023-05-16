package woowacourse.shopping.presentation.common

interface CounterContract {
    interface Presenter {
        fun plusCount()
        fun minusCount()
    }

    interface View {
        fun setCounterText(number: Int)
    }
}
