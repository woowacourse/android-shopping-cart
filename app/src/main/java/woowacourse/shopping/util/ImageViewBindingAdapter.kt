package woowacourse.shopping.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageSrc")
fun setImageResource(imageView: ImageView, src: String) {
    Glide.with(imageView.context).load(src).centerCrop().into(imageView)
}
