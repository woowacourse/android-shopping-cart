package woowacourse.shopping.feature.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutCountBinding

class CountView @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null
) : LinearLayout(context, attributesSet) {
    private val binding: LayoutCountBinding =
        LayoutCountBinding.inflate(LayoutInflater.from(context), this, true)

    var count: Int = 1
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
}
