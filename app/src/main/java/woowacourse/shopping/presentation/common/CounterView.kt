package woowacourse.shopping.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.findViewTreeLifecycleOwner
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
    val plusButton = binding.textCounterPlus
    val minusButton = binding.textCounterMinus
    val presenter: CounterContract.Presenter = CounterPresenter(this)
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.presenter = presenter
        binding.lifecycleOwner = findViewTreeLifecycleOwner()
    }

    override fun setCounterVisibility(visible: Boolean) {
        if (visible) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }
}
