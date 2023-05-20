package woowacourse.shopping.common_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
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
            binding.counterView.count = START_BUTTON_INIT_VALUE
            countStateChangeListener.onCountChanged(binding.counterView, START_BUTTON_INIT_VALUE)
            updateViewState(START_BUTTON_INIT_VALUE)
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

    companion object {
        private const val START_BUTTON_INIT_VALUE = 1
    }
}
