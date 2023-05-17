package woowacourse.shopping.common_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import woowacourse.shopping.databinding.LayoutProductCounterBinding

class ProductCounterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutProductCounterBinding by lazy {
        LayoutProductCounterBinding.inflate(LayoutInflater.from(context), this, true)
    }

    private val defaultClickListener = object : CounterView.OnCountStateChangeListener {
        override fun onCountChanged(counterNavigationView: CounterView?, count: Int) {
            updateViewState(count)
        }
    }

    var countStateChangeListener: CounterView.OnCountStateChangeListener = defaultClickListener
        set(value) {
            binding.counterView.countStateChangeListener =
                object : CounterView.OnCountStateChangeListener {
                    override fun onCountChanged(
                        counterNavigationView: CounterView?,
                        count: Int
                    ) {
                        // 카운터 뷰한테서 받은 카운트로 뷰 상태 업데이트
                        updateViewState(count)

                        // 등록받은 리스너 호출해서 바뀐 카운트값을 외부에 전달
                        value.onCountChanged(counterNavigationView, count)
                    }
                }
            field = value
        }

    init {
        binding.counterStartButton.setOnClickListener {
            binding.counterView.count = 1
            countStateChangeListener.onCountChanged(binding.counterView, 1)
            updateViewState(1)
        }
        binding.counterView.visibility = View.GONE
    }

    private fun updateViewState(count: Int) {
        if (count == 0) {
            binding.counterStartButton.visibility = View.VISIBLE
            binding.counterView.visibility = View.GONE
        } else {
            binding.counterStartButton.visibility = View.GONE
            binding.counterView.visibility = View.VISIBLE
        }
    }

    fun setCountState(count: Int, isActionListener: Boolean = true) {
        binding.counterView.setCountState(count, isActionListener)
        updateViewState(count)
    }

    fun setMinCountValue(minValue: Int) {
        binding.counterView.minCountValue = minValue
    }

    fun setMaxCountValue(maxValue: Int) {
        binding.counterView.maxCountValue = maxValue
    }


    companion object {
        @JvmStatic
        @BindingAdapter("counterViewMinValue")
        fun setProductCounterViewMinValue(productCounterView: ProductCounterView, value: Int) {
            productCounterView.setMinCountValue(value)
        }

        @JvmStatic
        @BindingAdapter("counterViewMaxValue")
        fun setProductCounterViewMaxValue(productCounterView: ProductCounterView, value: Int) {
            productCounterView.setMaxCountValue(value)
        }
    }
}