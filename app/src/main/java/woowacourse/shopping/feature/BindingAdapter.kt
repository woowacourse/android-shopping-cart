package woowacourse.shopping.feature

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun loadImageFromUrl(
    imageView: ImageView,
    url: String,
) {
    Glide.with(imageView.context).load(url).into(imageView)
}
