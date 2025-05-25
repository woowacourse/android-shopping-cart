package woowacourse.shopping.util

import androidx.databinding.BindingAdapter
import woowacourse.shopping.ui.custom.CartCountView

@BindingAdapter("count")
fun setCount(
    view: CartCountView,
    count: Int,
) {
    view.setCount(count)
}
