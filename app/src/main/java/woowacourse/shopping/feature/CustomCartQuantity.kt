package woowacourse.shopping.feature

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import woowacourse.shopping.databinding.LayoutCartQuantityBinding

class CustomCartQuantity(
    context: Context,
    attributeSet: AttributeSet,
) : LinearLayout(context, attributeSet) {
    private val binding: LayoutCartQuantityBinding by lazy { LayoutCartQuantityBinding.inflate(LayoutInflater.from(context), this, true) }
    private lateinit var clickListener: CartQuantityClickListener

    fun setCount(count: Int) {
        binding.tvQuantity.text = count.toString()
    }

    fun setClickListener(cartQuantityClickListener: CartQuantityClickListener) {
        clickListener = cartQuantityClickListener
        binding.cartClickListener = clickListener
    }

    interface CartQuantityClickListener {
        fun onAddClick()

        fun onRemoveClick()
    }
}
