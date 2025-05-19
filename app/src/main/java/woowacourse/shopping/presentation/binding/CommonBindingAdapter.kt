package woowacourse.shopping.presentation.binding

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

@BindingAdapter("isGone")
fun setVisibilityGone(
    view: View,
    isGone: Boolean,
) {
    if (isGone) view.visibility = GONE else VISIBLE
}
