package woowacourse.shopping.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ViewCounterBinding
import kotlin.properties.Delegates

class CounterView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var onCountChangedListener: OnCountChangedListener? = null
    private val binding: ViewCounterBinding by lazy {
        ViewCounterBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private var count: Int by Delegates.observable(INITIAL_COUNT) { _, old, new ->
        binding.countTextView.text = new.toString()
        onCountChangedListener?.countChanged(old, new)
    }
    private var minCount: Int = DEFAULT_MIN_COUNT
    private var maxCount: Int = DEFAULT_MAX_COUNT

    init {
        binding.count = count
        binding.counterMinusButton.setOnClickListener { if (count > minCount) --count }
        binding.counterPlusButton.setOnClickListener { if (count < maxCount) ++count }
        initTypedArrayValue(attrs)
    }

    private fun initTypedArrayValue(attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.CounterView).use {
            count = it.getInt(R.styleable.CounterView_count, INITIAL_COUNT)
            minCount = it.getInt(R.styleable.CounterView_min_count, DEFAULT_MIN_COUNT)
            maxCount = it.getInt(R.styleable.CounterView_max_count, DEFAULT_MAX_COUNT)
        }
    }

    companion object {
        private const val INITIAL_COUNT: Int = 1
        private const val DEFAULT_MIN_COUNT = 1
        private const val DEFAULT_MAX_COUNT = 99
    }

    interface OnCountChangedListener {
        fun countChanged(prevCount: Int, currentCount: Int)
    }
}
