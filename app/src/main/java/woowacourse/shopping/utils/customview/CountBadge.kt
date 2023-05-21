package woowacourse.shopping.utils.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.databinding.LayoutCountBadgeBinding

class CountBadge(
    context: Context,
    attrs: AttributeSet?
) : ConstraintLayout(context, attrs) {
    private val binding =
        LayoutCountBadgeBinding.inflate(LayoutInflater.from(context), this, true)

    var count: Int
        get() = binding.tvCartCount.toString().toInt()
        set(value) {
            binding.tvCartCount.text = value.toString()
        }
}