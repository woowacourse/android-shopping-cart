package woowacourse.shopping

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import woowacourse.shopping.databinding.CustomCountBinding

class CountCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) :

    ConstraintLayout(context, attrs, defStyleAttr) {
    val binding = CustomCountBinding.inflate(LayoutInflater.from(context), this, true)
}
