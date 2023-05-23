package woowacourse.shopping.util.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R
import woowacourse.shopping.model.UiPrice

@BindingAdapter("bind:price")
fun TextView.setPrice(price: UiPrice?) {
    text = context.getString(R.string.price_format, price?.value)
}
