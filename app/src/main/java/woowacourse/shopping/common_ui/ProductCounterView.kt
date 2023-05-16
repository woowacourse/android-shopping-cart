package woowacourse.shopping.common_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R
import woowacourse.shopping.databinding.LayoutProductCounterBinding

class ProductCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutProductCounterBinding by lazy {
        LayoutProductCounterBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var countStateChangeListener: CounterView.OnCountStateChangeListener? = null
        set(value) {
            binding.counterView.countStateChangeListener =
                object : CounterView.OnCountStateChangeListener {
                    override fun onCountMinusChanged(
                        counterNavigationView: CounterView?,
                        count: Int
                    ) {
                        changeViewState(count)
                        value?.onCountMinusChanged(counterNavigationView, count)
                    }

                    override fun onCountPlusChanged(
                        counterNavigationView: CounterView?,
                        count: Int
                    ) {
                        changeViewState(count)
                        value?.onCountPlusChanged(counterNavigationView, count)
                    }
                }
            field = value
        }

    @ColorRes
    var contentColor: Int = R.color.black
        set(value) {
            field = value
            binding.counterView.contentColor = value
        }

    init {
        binding.counterStartButton.setOnClickListener {
            binding.counterView.count = 1
            countStateChangeListener?.onCountPlusChanged(binding.counterView, 1)
            changeViewState(1)
        }
        binding.counterView.visibility = View.GONE
    }

    private fun changeViewState(count: Int) {
        if (count == 0) {
            binding.counterStartButton.visibility = View.VISIBLE
            binding.counterView.visibility = View.GONE
        } else {
            binding.counterStartButton.visibility = View.GONE
            binding.counterView.visibility = View.VISIBLE
        }
    }

    fun setCountState(count: Int) {
        binding.counterView.count = count
        changeViewState(count)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("contentColor")
        fun setContentColor(cartCounterView: ProductCounterView, color: Int) {
            cartCounterView.contentColor = color
        }
    }
}