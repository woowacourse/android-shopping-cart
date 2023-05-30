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
        binding.tvCount.text = count.toString()
    }

    fun initCount(initCount: Int) {
        count = initCount
        binding.tvCount.text = count.toString()
    }

    private fun setOnButtonClick() {
        binding.btnPlus.setOnClickListener {
            count++
            listener?.updateCount(this, count)
            updateCountView()
        }

        binding.btnMinus.setOnClickListener {
            count = maxOf(count - 1, ZERO_COUNT)
            count = listener?.updateCount(this, count)!!
            updateCountView()
        }
    }

    fun updateCountView() {
        binding.tvCount.text = count.toString()
    }

    companion object {
        private const val INIT_COUNT = 1
        private const val ZERO_COUNT = 0
    }
}
