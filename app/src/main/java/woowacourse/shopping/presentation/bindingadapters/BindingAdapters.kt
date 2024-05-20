package woowacourse.shopping.presentation.bindingadapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

@BindingAdapter("app:imageUrl")
fun loadImage(
    view: ImageView,
    url: String?,
) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(view)
    }
}

@BindingAdapter("app:price")
fun setPrice(
    view: TextView,
    price: Long?,
) {
    price?.let {
        view.text = view.context.getString(R.string.price_format, it)
    } ?: run {
        view.text = ""
    }
}
