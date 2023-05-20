package woowacourse.shopping

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.databinding.CustomCountBinding

class CustomCountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val binding = CustomCountBinding.inflate(LayoutInflater.from(context), this, true)

    var count: Int = 1
        set(value) {
            val validatedValue = if (value >= 1) value else 1
            binding.tvCount.text = validatedValue.toString()
            field = validatedValue
        }

    var plusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.tvPlus.setOnClickListener {
                value?.invoke()
                count++
            }
        }

    var minusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.tvMinus.setOnClickListener {
                value?.invoke()
                count--
            }
        }
}
