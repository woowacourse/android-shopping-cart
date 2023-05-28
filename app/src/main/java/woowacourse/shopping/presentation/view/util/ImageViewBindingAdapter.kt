package woowacourse.shopping.presentation.view.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageUrl")
fun setImageView(imageView: ImageView, imageUrl: String?) {
    imageUrl ?: return
    Glide.with(imageView.context).load(imageUrl).into(imageView)
}
