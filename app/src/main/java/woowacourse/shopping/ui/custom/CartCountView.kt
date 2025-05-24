package woowacourse.shopping.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutCartCountBinding

class CartCountView(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {
    private val binding: LayoutCartCountBinding by lazy { LayoutCartCountBinding.inflate(LayoutInflater.from(context), this, true) }
    private var onClickHandler: OnClickHandler? = null

    fun setCount(count: Int) {
        binding.cartCountText.text = count.toString()
    }

    fun setOnClickHandler(onClickHandler: OnClickHandler) {
        this.onClickHandler = onClickHandler
        binding.onClickHandler = onClickHandler
    }

    interface OnClickHandler {
        fun onIncreaseClick()

        fun onDecreaseClick()
    }
}
