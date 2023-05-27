package woowacourse.shopping.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.databinding.CustomCounterBinding

class CounterView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
) : ConstraintLayout(context, attr), CounterContract.View {

    private val binding: CustomCounterBinding = CustomCounterBinding.inflate(
        LayoutInflater.from(context),
        this,
        true,
    )
    private lateinit var presenter: CounterContract.Presenter
    val count: Int get() = binding.textCounterNumber.text.toString().toInt()

    fun setUpView(counterListener: CounterListener, initCount: Int, minimumCount: Int) {
        presenter = CounterPresenter(
            view = this,
            initCount = initCount,
            minimumCount = minimumCount,
            counterListener = counterListener,
        )
        presenter.checkCounterVisibility()
        onPlusClick()
        onMinusClick()
    }

    private fun onPlusClick() {
        binding.textCounterPlus.setOnClickListener {
            presenter.plusCount()
        }
    }

    private fun onMinusClick() {
        binding.textCounterMinus.setOnClickListener {
            presenter.minusCount()
            presenter.checkCounterVisibility()
        }
    }

    override fun setCountText(count: Int) {
        binding.textCounterNumber.text = count.toString()
    }

    override fun setCounterVisibility(visible: Boolean) {
        if (visible) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }
}
