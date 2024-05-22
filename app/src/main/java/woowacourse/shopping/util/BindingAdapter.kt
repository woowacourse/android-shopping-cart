package woowacourse.shopping.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("loadImage")
fun loadImage(
    view: ImageView,
    imageUrl: String?,
) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    } else {
        view.setImageDrawable(null)
    }
}

@BindingAdapter("formattedPrice")
fun setFormattedPrice(
    view: TextView,
    price: Int?,
) {
    price?.let {
        val context = view.context
        val formattedPrice = context.getString(R.string.product_price_format, it)
        view.text = formattedPrice
    }
}
