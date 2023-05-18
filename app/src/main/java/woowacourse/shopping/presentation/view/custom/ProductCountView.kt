package woowacourse.shopping.presentation.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.databinding.LayoutProductCountViewBinding

class ProductCountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding =
        LayoutProductCountViewBinding.inflate(LayoutInflater.from(context), this, true)

    private val defaultClickListener = object : CountView.OnCountStateChangeListener {
        override fun onCountChanged(countView: CountView?, count: Int) {
            updateCountViewVisibility(count)
        }
    }

    var countStateChangeListener: CountView.OnCountStateChangeListener = defaultClickListener
        set(value) {
            binding.countViewProductCount.countStateChangeListener =
                object : CountView.OnCountStateChangeListener {
                    override fun onCountChanged(
                        countView: CountView?,
                        count: Int
                    ) {
                        updateCountViewVisibility(count)

                        value.onCountChanged(countView, count)
                    }
                }
            field = value
        }

    init {
        binding.fabProductCount.setOnClickListener {
            binding.countViewProductCount.updateCount(DEFAULT_COUNT)
            countStateChangeListener.onCountChanged(binding.countViewProductCount, DEFAULT_COUNT)
            updateCountViewVisibility(DEFAULT_COUNT)
        }
        binding.countViewProductCount.visibility = View.GONE
    }

    fun updateCountViewVisibility(count: Int) {
        if (count == 0) {
            binding.fabProductCount.visibility = View.VISIBLE
            binding.countViewProductCount.visibility = View.GONE
        } else {
            binding.fabProductCount.visibility = View.GONE
            binding.countViewProductCount.visibility = View.VISIBLE
        }
    }

    fun setMinCount(minCount: Int) {
        binding.countViewProductCount.setMinCount(minCount)
    }

    fun setMaxCount(maxCount: Int) {
        binding.countViewProductCount.setMaxCount(maxCount)
    }

    fun setCount(count: Int) {
        binding.countViewProductCount.updateCount(count)
        updateCountViewVisibility(count)
    }

    companion object {
        private const val DEFAULT_COUNT = 1
    }
}
