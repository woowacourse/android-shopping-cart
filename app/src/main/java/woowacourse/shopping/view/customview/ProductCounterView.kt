package woowacourse.shopping.view.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.shopping.domain.Count
import woowacourse.shopping.databinding.ItemProductCounterBinding

class ProductCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: ItemProductCounterBinding =
        ItemProductCounterBinding.inflate(LayoutInflater.from(context), this, true)

    var productCounterListener: ProductCounterViewEventListener? = null
    var counterListener: CounterViewEventListener? = null

    init {
        setOnButtonClick()
    }

    private fun setOnButtonClick() {
        binding.productCounter.listener = object: CounterViewEventListener {
            override fun updateCount(counterView: CounterView, count: Int) {
                binding.productCounter.updateCountView()
                setViewVisibility(count)
                counterListener?.updateCount(counterView, count)
            }
        }

        binding.productToCart.setOnClickListener {
            binding.productCounter.initCount(INIT_COUNT_VALUE)
            setViewVisibility(INIT_COUNT_VALUE)
            productCounterListener?.onAddToCartButtonClick()
        }
    }

    fun initCountView(count: Int) {
        binding.productCounter.initCount(count)
    }

    fun setViewVisibility(count: Int) {
        if (count == 0) {
            binding.productToCart.visibility = VISIBLE
            binding.productCounter.visibility = GONE
        } else {
            binding.productToCart.visibility = GONE
            binding.productCounter.visibility = VISIBLE
        }
    }

    companion object {
        private const val INIT_COUNT_VALUE = 1
    }
}
