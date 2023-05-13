package woowacourse.shopping.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBindingAdapter {
    @BindingAdapter("imageSrc")
    @JvmStatic
    fun setImageResource(imageView: ImageView, src: String) {
        Glide.with(imageView.context).load(src).centerCrop().into(imageView)
    }
}