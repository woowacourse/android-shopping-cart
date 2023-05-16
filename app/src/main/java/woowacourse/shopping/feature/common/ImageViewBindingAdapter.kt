package woowacourse.shopping.feature.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun setImgUrl(imageView: ImageView, imgUrl: String) {
    Glide.with(imageView.context)
        .load(imgUrl)
        .into(imageView)
}
