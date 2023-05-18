package woowacourse.shopping.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
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
        }
    }

    private fun minusCount() {
        binding.buttonMinusProductCount.setOnClickListener {
            listener?.onMinus()
        }
    }

    fun setTextCount(count: Int) {
        binding.textProductCount.text = count.toString()
    }

    fun setListener(listener: CountPickerListener) {
        this.listener = listener
    }
}
