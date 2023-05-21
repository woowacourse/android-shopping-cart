package woowacourse.shopping.utils.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutCounterBinding

class Counter(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    val binding = LayoutCounterBinding.inflate(LayoutInflater.from(context), this, true)

    var count: Int
        get() = binding.tvCount.text.toString().toInt()
        set(value) {
            binding.tvCount.text = value.toString()
        }

    val tvPlus = binding.tvPlus
    val tvMinus = binding.tvMinus
}