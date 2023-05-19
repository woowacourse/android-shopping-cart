package woowacourse.shopping.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import woowacourse.shopping.R
import woowacourse.shopping.databinding.CountPickerBinding

class CountPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {

    private val binding: CountPickerBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.count_picker,
        this,
        true
    )
    private var listener: CountPickerListener? = null

    init {
        plusCount()
        minusCount()
    }

    private fun plusCount() {
        binding.buttonPlusProductCount.setOnClickListener {
            listener?.onPlus()
            binding.textProductCount.plusCount()
        }
    }

    private fun minusCount() {
        binding.buttonMinusProductCount.setOnClickListener {
            listener?.onMinus()
            binding.textProductCount.minusCount()
        }
    }

    fun setTextCount(count: Int) {
        binding.textProductCount.text = count.toString()
    }

    fun setListener(listener: CountPickerListener) {
        this.listener = listener
    }

    private fun TextView.plusCount() {
        val count = text.toString().toInt() + 1

        text = count.toString()
    }

    private fun TextView.minusCount() {
        val count = text.toString().toInt() - 1
        if (count != 0) {
            text = count.toString()
        }
    }
}
