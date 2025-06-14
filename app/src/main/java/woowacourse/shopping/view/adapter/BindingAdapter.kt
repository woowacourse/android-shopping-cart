package woowacourse.shopping.view.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:imageUrl")
fun setImageUrl(
    imageView: ImageView,
    imageUrl: String,
) {
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .into(imageView)
}

@BindingAdapter("app:quantity")
fun setQuantity(
    view: TextView,
    quantity: Int?,
) {
    view.text = (quantity ?: 1).toString()
}
