package woowacourse.shopping.presentation.view.custom

class CountPresenter(private val view: CountContract.View) : CountContract.Presenter {
    private var count = MIN_COUNT
    var minCountValue: Int = MIN_COUNT
        set(value) {
            field = value.coerceAtLeast(MIN_COUNT)
        }
    var maxCountValue: Int = MAX_COUNT
        set(value) {
            field = value.coerceAtMost(MAX_COUNT)
        }

    init {
        view.setMinusButton()
        view.setPlusButton()
        view.setCountTextView(count)
    }

    override fun updateCount(count: Int) {
        this.count = count
        view.setCountTextView(count)
    }

    override fun getCount(): Int = count

    override fun updateMinusCount() {
        count = count.dec().coerceAtLeast(minCountValue)
        view.setCountTextView(count)
    }

    override fun updatePlusCount() {
        count = count.inc().coerceAtMost(maxCountValue)
        view.setCountTextView(count)
    }

    companion object {
        private const val MIN_COUNT = 0
        private const val MAX_COUNT = 99
    }
}
