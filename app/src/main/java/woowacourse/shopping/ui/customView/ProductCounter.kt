package woowacourse.shopping.ui.customView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutProductCounterBinding

class ProductCounter @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    val binding = LayoutProductCounterBinding.inflate(LayoutInflater.from(context), this, true)

    private var onCountChangeListener: (Int, Int) -> Unit = { _, _ -> }

    var minCount: Int = 0
    var maxCount: Int = 99
    var productId: Int = 0
    var count: Int
        get() = binding.tvCount.text.toString().toInt()
        set(value) {
            binding.tvCount.text = when {
                value < minCount -> minCount.toString()
                value > maxCount -> maxCount.toString()
                else -> {
                    onCountChangeListener(productId, value)
                    value.toString()
                }
            }
        }

    init {
        initView()
    }

    fun setOnCountChangeListener(listener: (Int, Int) -> Unit) {
        onCountChangeListener = listener
    }

    private fun initView() {
        binding.tvCount.text = "0"
        binding.tvPlus.setOnClickListener { count += 1 }
        binding.tvMinus.setOnClickListener { count -= 1 }
    }
}
