package woowacourse.shopping.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import woowacourse.shopping.databinding.ViewCounterBinding

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCounterBinding by lazy {
        ViewCounterBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var count: Int = DEFAULT_MIN_COUNT_VALUE
        set(value) {
            if (value < DEFAULT_MIN_COUNT_VALUE) return
            field = value
            binding.tvProductCount.text = field.toString()
        }

    var minCountValue: Int = DEFAULT_MIN_COUNT_VALUE
        set(value) {
            require(DEFAULT_MAX_COUNT_VALUE > value) {}
            field = value
        }

    val countTextView: TextView by lazy { binding.tvProductCount }
    val countUpButton: TextView by lazy { binding.btCountUp }
    val countDownButton: TextView by lazy { binding.btCountDown }

    companion object {
        private const val DEFAULT_MIN_COUNT_VALUE = 0
        private const val DEFAULT_MAX_COUNT_VALUE = 99
    }
}
