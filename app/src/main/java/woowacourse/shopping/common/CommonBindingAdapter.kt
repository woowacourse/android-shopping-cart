package woowacourse.shopping.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CommonBindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(
        imageView: ImageView,
        url: String?,
    ) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}
