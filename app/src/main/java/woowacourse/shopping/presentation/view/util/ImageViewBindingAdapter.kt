package woowacourse.shopping.presentation.view.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("set_image_url")
fun setImageView(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context).load(imageUrl).into(imageView)
}
