package woowacourse.shopping.presentation.view.custom

interface CountContract {
    interface View {
        fun setMinusButton()
        fun setPlusButton()
        fun setCountTextView(count: Int)
        fun updateCount(count: Int)
        fun getCount(): Int
    }
    interface Presenter {
        fun updateCount(count: Int)
        fun getCount(): Int
        fun updateMinusCount()
        fun updatePlusCount()
    }
}
