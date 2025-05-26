package woowacourse.shopping.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutCartCountBinding

class CartCountView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : LinearLayout(context, attrs, defStyleAttr) {
        private val binding: LayoutCartCountBinding by lazy { LayoutCartCountBinding.inflate(LayoutInflater.from(context), this, true) }

        fun setCount(count: Int) {
            binding.count = count
        }

        fun setOnClickHandler(onClickHandler: OnClickHandler) {
            binding.onClickHandler = onClickHandler
        }

        interface OnClickHandler {
            fun onIncreaseClick()

            fun onDecreaseClick()
        }
    }
