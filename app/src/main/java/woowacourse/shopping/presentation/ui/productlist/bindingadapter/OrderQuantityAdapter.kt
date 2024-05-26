package woowacourse.shopping.presentation.ui.productlist.bindingadapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("showOrderQuantity")
fun TextView.showOrderQuantity(orderQuantity: Int) {
    if (orderQuantity <= 0) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
        text = orderQuantity.toString()
    }
}
