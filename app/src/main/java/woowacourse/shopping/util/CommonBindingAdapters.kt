package woowacourse.shopping.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CommonBindingAdapters {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun setImageResource(
        view: ImageView,
        imageUrl: String,
    ) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    }
}
