package woowacourse.shopping.feature

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.databinding.ItemLastViewedBinding

class CustomLastViewed(
    context: Context,
    attributeSet: AttributeSet,
) : ConstraintLayout(context, attributeSet) {
    private val binding: ItemLastViewedBinding by lazy { ItemLastViewedBinding.inflate(LayoutInflater.from(context), this, true) }
    private lateinit var clickListener: LastViewedClickListener

    fun setName(name: String) {
        binding.tvLastViewedName.text = name
    }

    fun setClickListener(lastViewedClickListener: LastViewedClickListener) {
        clickListener = lastViewedClickListener
        binding.lastViewedClickListener = clickListener
    }

    interface LastViewedClickListener {
        fun navigate()
    }
}
