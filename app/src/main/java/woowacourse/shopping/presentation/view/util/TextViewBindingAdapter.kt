package woowacourse.shopping.presentation.view.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.shopping.R

@BindingAdapter("setText")
fun setTextView(textView: TextView, text: String?) {
    textView.text = text ?: ""
}

@BindingAdapter("setPrice")
fun setPriceView(textView: TextView, price: Int?) {
    textView.text = textView.context.getString(R.string.product_price_format, price ?: 0)
}
