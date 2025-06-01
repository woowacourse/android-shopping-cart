package woowacourse.shopping.presentation.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R

@BindingAdapter("price", "count")
fun setFormattedPrice(
    view: TextView,
    price: Int,
    count: Int,
) {
    val total = price * count
    val context = view.context
    view.text = context.getString(R.string.product_detail_price, total)
}
