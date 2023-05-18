package woowacourse.shopping.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.shopping.domain.Count
import woowacourse.shopping.databinding.ItemCounterBinding

class CounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var listener: CounterViewEventListener? = null
    private var count: Count = Count(INIT_COUNT)
    private val binding: ItemCounterBinding =
        ItemCounterBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setOnButtonClick()
        binding.tvCount.text = count.value.toString()
    }

    private fun setOnButtonClick() {
        binding.btnPlus.setOnClickListener {
            count = count.inc()
            listener?.addCount(this, count.value)
        }

        binding.btnMinus.setOnClickListener {
            count = count.dec()
            listener?.decCount(this, count.value)
        }
    }

    fun updateCountView() {
        binding.tvCount.text = count.value.toString()
    }

    companion object {
        private const val INIT_COUNT = 1
    }
}
