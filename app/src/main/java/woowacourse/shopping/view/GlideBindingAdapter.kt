package woowacourse.shopping.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target

@BindingAdapter("imageUrl")
fun loadImage(
    view: ImageView,
    imageUrl: String,
) {
    Glide.with(view.context)
        .load(imageUrl)
        .override(Target.SIZE_ORIGINAL)
        .into(view)
}
