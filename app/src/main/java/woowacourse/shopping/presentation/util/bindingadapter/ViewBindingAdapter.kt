package woowacourse.shopping.presentation.util.bindingadapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("isVisible")
fun View.bindIsVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}
