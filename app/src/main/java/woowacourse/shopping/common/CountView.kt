package woowacourse.shopping.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.CountBinding

class CountView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
) : LinearLayout(context, attributeSet) {
    private val binding = CountBinding.inflate(LayoutInflater.from(context), this, true)

    var count: Int = MIN_COUNT_NUMBER
        set(value) {
            field = value
            binding.countTv.text = value.toString()
        }

    var plusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.plusBtn.setOnClickListener {
                value?.invoke()
                count++
            }
        }

    var minusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.minusBtn.setOnClickListener {
                value?.invoke()
                count--
            }
        }

    companion object {
        private const val MIN_COUNT_NUMBER = 1
    }
}
