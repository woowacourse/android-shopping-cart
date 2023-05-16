package woowacourse.shopping.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
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
    private val presenter = CounterPresenter(this)
    val plusButton = binding.textCounterPlus
    val minusButton = binding.textCounterMinus
    val countText = binding.textCounterNumber

    init {
        binding.textCounterMinus.setOnClickListener { presenter.minusCount() }
        binding.textCounterPlus.setOnClickListener { presenter.plusCount() }
    }

    override fun setCounterText(number: Int) {
        binding.textCounterNumber.text = number.toString()
    }
}
