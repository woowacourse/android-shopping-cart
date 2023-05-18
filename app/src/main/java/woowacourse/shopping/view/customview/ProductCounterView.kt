package woowacourse.shopping.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.databinding.ProductCounterBinding

class ProductCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ProductCounterBinding =
        ProductCounterBinding.inflate(LayoutInflater.from(context), this, true)

    var listener = object : CounterViewEventListener {
        override fun addCount(counterView: CounterView, count: Int) {
            setViewVisibility(count)
        }

        override fun decCount(counterView: CounterView, count: Int) {
            setViewVisibility(count)
        }
    }

    init {
        binding.productCounter.listener = listener
    }

    private fun setViewVisibility(count: Int) {
        if (count == 0) {
            binding.productToCart.visibility = VISIBLE
            binding.productCounter.visibility = GONE
        } else {
            binding.productToCart.visibility = GONE
            binding.productCounter.visibility = VISIBLE
        }
    }
}
