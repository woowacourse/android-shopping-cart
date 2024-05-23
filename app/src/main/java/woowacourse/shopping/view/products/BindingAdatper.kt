package woowacourse.shopping.view.products

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:visibility")
fun setVisibility(
    view: View,
    count: Int,
) {
    Log.d("yenny", "count : $count")
    view.visibility = if (count < 1) View.GONE else View.VISIBLE
}
