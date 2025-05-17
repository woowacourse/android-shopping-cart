package woowacourse.shopping.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import woowacourse.shopping.R

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(
        imageView: ImageView,
        imageUrl: String?,
    ) {
        Glide
            .with(imageView)
            .load(imageUrl)
            .placeholder(R.drawable.iced_americano)
            .fallback(R.drawable.iced_americano)
            .error(R.drawable.iced_americano)
            .into(imageView)
    }
}
