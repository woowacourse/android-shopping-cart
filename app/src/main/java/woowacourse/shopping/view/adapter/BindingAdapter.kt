package woowacourse.shopping.view.adapter

import android.view.View
import android.widget.ImageView
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

@BindingAdapter("app:visibility")
fun changeVisibility(
    view: View,
    visible: Boolean,
) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}
