package woowacourse.shopping.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.CompoundCounterBinding

class Counter(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {
    private val binding: CompoundCounterBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.compound_counter, this, true
    )

    private var count: Int = 0

    companion object {
        @BindingAdapter("onMinusClick")
        @JvmStatic
        fun minusClick(counter: Counter, onClick: (Int) -> Unit) {
            counter.binding.counterMinusButton.setOnClickListener {
                counter.count -= 1
                onClick(counter.count)
            }
        }

        @BindingAdapter("onPlusClick")
        @JvmStatic
        fun plusClick(counter: Counter, onClick: (Int) -> Unit) {
            counter.binding.counterPlusButton.setOnClickListener {
                counter.count -= 1
                onClick(counter.count)
            }
        }

        @BindingAdapter("count")
        @JvmStatic
        fun setCounterText(counter: Counter, count: Int) {
            counter.count = count
            counter.binding.counterText.text = count.toString()
        }
    }
}
