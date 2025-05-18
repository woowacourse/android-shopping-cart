package woowacourse.shopping.view.mapper

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("android:price")
fun setPrice(
    view: TextView,
    price: Int,
) {
    view.text = view.context.getString(R.string.template_price, price)
}

@BindingAdapter("android:image")
fun setImage(
    view: ImageView,
    url: String,
) {
    Glide.with(view.context)
        .load(url)
        .placeholder(R.drawable.placeholder_product)
        .into(view)
}
