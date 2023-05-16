package woowacourse.shopping.common_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R
import woowacourse.shopping.databinding.LayoutCounterBinding


class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: LayoutCounterBinding by lazy {
        LayoutCounterBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var count: Int = MIN_COUNT_VALUE
        set(value) {
            require(value in MIN_COUNT_VALUE..maxCountValue) { ERROR_COUNT_RANGE_OVER }
            field = value
            binding.countNumberTextView.text = field.toString()
        }

    var maxCountValue: Int = DEFAULT_MAX_COUNT_VALUE

    @ColorRes
    var contentColor: Int = R.color.black
        set(value) {
            binding.plusButton.setTextColor(resources.getColor(value, null))
            binding.minusButton.setTextColor(resources.getColor(value, null))
            binding.countNumberTextView.setTextColor(resources.getColor(value, null))
        }

    var countStateChangeListener: OnCountStateChangeListener? = null

    interface OnCountStateChangeListener {
        fun onCountMinusChanged(counterNavigationView: CounterView?, count: Int)
        fun onCountPlusChanged(counterNavigationView: CounterView?, count: Int)
    }

    init {
        binding.plusButton.setOnClickListener {
            if (count == maxCountValue) return@setOnClickListener
            count++
            countStateChangeListener?.onCountPlusChanged(this, count)
        }
        binding.minusButton.setOnClickListener {
            if (count == MIN_COUNT_VALUE) return@setOnClickListener
            count--
            countStateChangeListener?.onCountMinusChanged(this, count)
        }
    }

    companion object {
        private const val ERROR_COUNT_RANGE_OVER = "[ERROR] 카운트의 범위를 벗어났습니다"
        private const val MIN_COUNT_VALUE = 0
        private const val DEFAULT_MAX_COUNT_VALUE = 99

        @JvmStatic
        @BindingAdapter("counterContentColor")
        fun setCounterContentColor(counterView: CounterView, color: Int) {
            counterView.contentColor = color
        }
    }
}