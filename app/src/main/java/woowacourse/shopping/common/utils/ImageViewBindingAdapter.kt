package woowacourse.shopping.common.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageViewBindingAdapter {
    @BindingAdapter("android:urlSrc")
    @JvmStatic
    fun src(imageView: ImageView, src: String) {
        Glide.with(imageView.context).load(src).centerCrop().into(imageView)
    }
}
