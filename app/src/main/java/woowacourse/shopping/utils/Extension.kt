package woowacourse.shopping.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target

object Extension {
    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun setImageUrl(
        view: ImageView,
        url: String?,
    ) {
        if (!url.isNullOrEmpty()) {
            Glide.with(view.context)
                .load(url)
                .override(Target.SIZE_ORIGINAL)
                .into(view)
        }
    }
}
