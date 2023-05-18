package woowacourse.shopping.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.ViewCounterBinding
import woowacourse.shopping.model.CartProductState.Companion.MIN_COUNT_VALUE

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewCounterBinding by lazy {
        ViewCounterBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var count: Int = MIN_COUNT_VALUE
        set(value) {
            field = value
            binding.countNumberTv.text = field.toString()
        }

    var plusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.plusTv.setOnClickListener { value?.invoke() }
        }

    var minusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.minusTv.setOnClickListener { value?.invoke() }
        }
}
