package woowacourse.shopping.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.ItemCounterBinding

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var listener: CounterViewEventListener? = null
    private var count: Int = INIT_COUNT
    private val binding: ItemCounterBinding =
        ItemCounterBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setOnButtonClick()
    }

    private fun setOnButtonClick() {
        binding.btnMinus.setOnClickListener {
            count++
            listener?.addCount(this, count)
        }

        binding.btnPlus.setOnClickListener {
            count--
            listener?.decCount(this, count)
        }
    }

    companion object {
        private const val INIT_COUNT = 0
    }
}
