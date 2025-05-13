package woowacourse.shopping.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageBindingAdapter(
    imageView: ImageView,
    imageUrl: String,
) {
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .into(imageView)
}
