package woowacourse.shopping.presentation.ui.productlist.adapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("hideIfCountLessThanZero")
fun View.hideIfCountLessThanZero(count: Int) {
    if (count <= 0) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
    }
}

@BindingAdapter("showIfCountLessThanZero")
fun View.showIfCountLessThanZero(count: Int) {
    if (count <= 0) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}
