package woowacourse.shopping.view.products.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibility")
fun setVisibility(
    view: View,
    count: Int,
) {
    view.visibility = if (count < 1) View.GONE else View.VISIBLE
}
