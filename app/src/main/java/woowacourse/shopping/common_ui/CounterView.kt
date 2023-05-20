package woowacourse.shopping.common_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import woowacourse.shopping.databinding.LayoutCounterBinding

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutCounterBinding by lazy {
        LayoutCounterBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var count: Int = MIN_COUNT_VALUE
        set(value) {
            require(value in minCountValue..DEFAULT_MAX_COUNT_VALUE) { ERROR_COUNT_RANGE_OVER }
            field = value
            binding.countNumberTextView.text = field.toString()
            countStateChangeListener?.onCountChanged(this, field)
        }

    // 카운터의 최소치 조정 가능
    var minCountValue: Int = DEFAULT_MIN_COUNT_VALUE
        set(value) {
            require(DEFAULT_MAX_COUNT_VALUE > value) {}
            field = value
        }

    var countStateChangeListener: OnCountStateChangeListener? = null

    interface OnCountStateChangeListener {
        fun onCountChanged(counterNavigationView: CounterView?, count: Int)
    }

    init {
        binding.plusButton.setOnClickListener {
            if (count == DEFAULT_MAX_COUNT_VALUE) return@setOnClickListener
            count++
        }
        binding.minusButton.setOnClickListener {
            if (count == minCountValue) return@setOnClickListener
            count--
        }
    }

    fun setCountState(count: Int, isActionListener: Boolean = true) {
        if (isActionListener.not()) {
            val listener = countStateChangeListener
            countStateChangeListener = null
            this.count = count
            countStateChangeListener = listener
        } else {
            this.count = count
        }
    }

    companion object {
        private const val ERROR_COUNT_RANGE_OVER = "[ERROR] 카운트의 범위를 벗어났습니다"
        private const val MIN_COUNT_VALUE = 0
        private const val DEFAULT_MIN_COUNT_VALUE = 0
        private const val DEFAULT_MAX_COUNT_VALUE = 99

        @JvmStatic
        @BindingAdapter("counterViewMinValue")
        fun setCounterViewMinValue(counterView: CounterView, value: Int) {
            counterView.minCountValue = value
        }
    }
}
