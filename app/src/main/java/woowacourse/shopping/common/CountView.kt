package woowacourse.shopping.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutCountBinding

class CountView @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null,
) : LinearLayout(context, attributesSet) {
    val binding = LayoutCountBinding.inflate(
        LayoutInflater.from(context),
        this,
        true,
    )

    var count: Int = 1
        set(value) {
            field = value
            binding.textProductCount.text = value.toString()
        }

    var plusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.buttonPlus.setOnClickListener {
                value?.invoke()
                count++
            }
        }

    var minusClickListener: (() -> Unit)? = null
        set(value) {
            field = value
            binding.buttonMinus.setOnClickListener {
                value?.invoke()
                count--
            }
        }
}
