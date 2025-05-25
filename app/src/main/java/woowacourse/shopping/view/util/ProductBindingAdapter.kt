package woowacourse.shopping.view.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("priceWithFormat")
fun setPrice(
    view: TextView,
    price: Int,
) {
    view.text = view.context.getString(R.string.template_price, price)
}

@BindingAdapter("productImage")
fun setImage(
    view: ImageView,
    url: String?,
) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.placeholder_product)
        .into(view)
}

@BindingAdapter("isGone")
fun setVisibilityGone(
    view: View,
    isGone: Boolean,
) {
    view.visibility = if (isGone) View.GONE else View.VISIBLE
}
