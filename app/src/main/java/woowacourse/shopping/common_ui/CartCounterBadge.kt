package woowacourse.shopping.common_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import woowacourse.shopping.databinding.LayoutCartCounterBadgeBinding

class CartCounterBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding: LayoutCartCounterBadgeBinding by lazy {
        LayoutCartCounterBadgeBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var count: Int = ZERO
        set(value) {
            require(value >= ZERO) { ERROR_COUNT_RANGE_OVER }
            field = value
            setCurrentState()
        }

    init {
        setCurrentState()
    }

    private fun setCurrentState() {
        binding.badge.text = "$count"
        if (count > COUNT_THRESHOLD) {
            binding.badge.visibility = View.GONE
            binding.badgePlusView.visibility = View.VISIBLE
        } else if (count == ZERO) {
            binding.badge.visibility = View.GONE
            binding.badgePlusView.visibility = View.GONE
        } else {
            binding.badge.visibility = View.VISIBLE
            binding.badgePlusView.visibility = View.GONE
        }
    }

    companion object {
        private const val ERROR_COUNT_RANGE_OVER = "[ERROR] 카운트 숫자는 0이상이여야 합니다"
        private const val ZERO = 0
        private const val COUNT_THRESHOLD = 99

        @JvmStatic
        @BindingAdapter("badgeCount")
        fun setCartCountBadgeState(cartCounterBadge: CartCounterBadge, count: Int) {
            cartCounterBadge.count = count
        }
    }
}
