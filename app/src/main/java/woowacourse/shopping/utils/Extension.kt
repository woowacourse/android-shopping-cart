package woowacourse.shopping.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target

@BindingAdapter("app:imageUrl")
fun setImageUrl(
    view: ImageView,
    url: String,
) {
    Glide.with(view.context)
        .load(url)
        .override(Target.SIZE_ORIGINAL)
        .into(view)
}
